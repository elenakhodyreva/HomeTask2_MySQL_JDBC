package ru.kai.servlets;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kai.dao.*;
import ru.kai.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@WebServlet("/users")
public class UsersServletWithDao extends HttpServlet {

    //private UsersDao usersDao;
    IndependentUserDAO usersDao;
    private PasswordEncoder passwordEncoder;
    String hashedPassword;
    User user;
    List<User> users;

    @Override
    public void init() throws ServletException {

//        DriverManagerDataSource dataSource =
//                new DriverManagerDataSource();
//        Properties properties = new Properties();
//
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

        passwordEncoder = new BCryptPasswordEncoder();
        ApplicationContext context = new ClassPathXmlApplicationContext("ru.kai\\context.xml");
        usersDao = context.getBean(IndependentUserDAO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        Optional<User> user = usersDao.find(1);

        if (req.getParameter("name") != null) {
            String name = req.getParameter("name");
            users = usersDao.findAllByFirstName(name);
        } else {
            users = usersDao.findAll();
        }

        req.setAttribute("usersFromServer", users);
        req.getServletContext().getRequestDispatcher("/jsp/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("name") != null
                && req.getParameter("password") != null
                && req.getParameter("birthDate") != null) {

            String name = req.getParameter("name");
            String password = req.getParameter("password");
            LocalDate birthDate = LocalDate.parse(req.getParameter("birthDate"));
            hashedPassword = passwordEncoder.encode(password);

            //создаю объект user с зашифрованным паролем и сохраняю в таком виде в БД
            user = new User(name, hashedPassword, birthDate);
            usersDao.save(user);
            //использую redirect чтобы в теле request не сохранилось имя name
            //иначе в повторном doGet вызовется findAllByFirstName
            resp.sendRedirect("/users");

        } else if (req.getParameter("newName") != null
                && req.getParameter("newPassword") != null) {
            hashedPassword = passwordEncoder.encode(req.getParameter("newPassword"));
            users = usersDao.findAllByFirstName(req.getParameter("newName"));
            if (users.size() != 0) {
                user = users.get(0);
                user.setPassword(hashedPassword);
                usersDao.update(user);
                resp.sendRedirect("/users");
            }
        } else if (req.getParameter("userIdDelete") != null) {
            usersDao.delete(Integer.parseInt(req.getParameter("userIdDelete")));
            resp.sendRedirect("/users");
        } else {
            doGet(req, resp);
        }
    }
}

