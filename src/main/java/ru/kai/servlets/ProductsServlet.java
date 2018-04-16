package ru.kai.servlets;

import org.hibernate.cfg.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kai.dao.BooksDao;
import ru.kai.dao.BooksDaoJDBCImpl;
import ru.kai.dao.BooksHibernateDaoImpl;
import ru.kai.models.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private BooksDao booksDao;
    private Configuration configuration = new Configuration();

    @Override
    public void init() throws ServletException {

        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();
        Properties properties = new Properties();

        try {
            //путь к файлу который задеплоился в томкат, а не к тому что в проекте
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");

            dataSource.setUrl(dbUrl);
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);
            dataSource.setDriverClassName(driverClassName);

            //hibernate
            configuration.setProperty("hibernate.connection.url", dbUrl);
            configuration.setProperty("hibernate.connection.username", dbUsername);
            configuration.setProperty("hibernate.connection.password", dbPassword);
            configuration.setProperty("hibernate.connection.driver_class", driverClassName);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            configuration.addResource("Book.hbm.xml");
            configuration.addAnnotatedClass(Book.class);
            configuration.setProperty("hibernate.show_sql", "true");

            //здесь реализация для Hibernate но есть и для обычной JDBC
            //booksDao = new BooksDaoJDBCImpl(dataSource);
            booksDao = new BooksHibernateDaoImpl(configuration);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        req.setAttribute("myBooks", booksDao.findAll());
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/jsp/products.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("bookName") != null
                && req.getParameter("bookAuthor") != null
                && isNumeric(req.getParameter("bookCount"))
                && isNumeric(req.getParameter("bookCost"))) {

            req.setCharacterEncoding("UTF-8");
            Book book = new Book();

            book.setName(req.getParameter("bookName"));
            book.setAuthor(req.getParameter("bookAuthor"));
            book.setCount(Integer.parseInt(req.getParameter("bookCount")));
            book.setCost(Integer.parseInt(req.getParameter("bookCost")));

            System.out.println(book.getName());


            booksDao.save(book);
            doGet(req, resp);

        } else {
            doGet(req, resp);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
