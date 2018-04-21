package ru.kai.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kai.dao.*;
import ru.kai.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private IndependentUserDAO usersDao;
    //класс проверяющий, есть ли такой пользователь с помощью BCrypt
    UserExistingChecker userExistingChecker;
    List<User> users;

    @Override
    public void init() throws ServletException {
//        DriverManagerDataSource dataSource =
//                new DriverManagerDataSource();
//        Properties properties = new Properties();
//        try {
//            //путь к файлу который задеплоился в томкат, а не к тому что в проекте
//            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
//            String dbUrl = properties.getProperty("db.url");
//            String dbUsername = properties.getProperty("db.username");
//            String dbPassword = properties.getProperty("db.password");
//            String driverClassName = properties.getProperty("db.driverClassName");
//
//            dataSource.setUrl(dbUrl);
//            dataSource.setUsername(dbUsername);
//            dataSource.setPassword(dbPassword);
//            dataSource.setDriverClassName(driverClassName);
//
//            usersDao= new UsersDaoJDBCImpl(dataSource);
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }

        ApplicationContext context= new ClassPathXmlApplicationContext("ru.kai\\context.xml");
        usersDao= context.getBean(IndependentUserDAO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name= req.getParameter("name");
        String password= req.getParameter("password");
        users= usersDao.findAll();

        //передаю список пользователей
        userExistingChecker= new UserExistingChecker(users);

        if(userExistingChecker.isExist(name, password)){
            HttpSession session= req.getSession();
            session.setAttribute("user", name);
            req.getServletContext().getRequestDispatcher("/home").forward(req,resp);
        }
        else {
            resp.sendRedirect(req.getContextPath()+"/login");
        }
    }
}
