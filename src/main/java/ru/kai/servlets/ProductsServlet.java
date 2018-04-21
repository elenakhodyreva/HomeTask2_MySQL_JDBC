package ru.kai.servlets;

import org.hibernate.cfg.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kai.dao.BooksDao;
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

            //здесь реализация для Hibernate
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
                && req.getParameter("bookCount") != null
                && req.getParameter("bookCost") != null) {

            req.setCharacterEncoding("UTF-8");
            Book book = new Book();

            book.setName(req.getParameter("bookName"));
            book.setAuthor(req.getParameter("bookAuthor"));
            book.setCount(Integer.parseInt(req.getParameter("bookCount")));
            book.setCost(Integer.parseInt(req.getParameter("bookCost")));

            System.out.println(book.getName());


            booksDao.save(book);
            doGet(req, resp);

        } else if (req.getParameter("newBookName") != null
                && req.getParameter("newBookAuthor") != null
                && req.getParameter("newCount") != null
                && req.getParameter("newCost") != null) {

            //сначала нашли книгу по имени
            Book newBook = booksDao.findByFirstName(req.getParameter("newBookName"));

            //затем отредактировали
            if (newBook != null){
                newBook.setCount(Integer.parseInt(req.getParameter("newCount")));
                newBook.setCost(Integer.parseInt(req.getParameter("newCost")));
                booksDao.update(newBook);
            }

            resp.sendRedirect("/products");

        } else if (req.getParameter("idForDelete") != null) {
            booksDao.delete(Integer.parseInt(req.getParameter("idForDelete")));
            resp.sendRedirect("/products");
        } else {
            doGet(req, resp);
        }
    }
}
