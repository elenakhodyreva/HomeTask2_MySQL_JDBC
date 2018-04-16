package ru.kai.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kai.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDaoJDBCImpl implements UsersDao {

    private Connection connection;

    public UsersDaoJDBCImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM fix_user";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM fix_user WHERE id=? ";

    //language=SQL
    private final String SQL_FIND_BY_NAME =
            "SELECT * from fix_user WHERE name=?";

    //language=SQL
    private final String SQL_INSERT_USER =
            "INSERT INTO fix_user (name, password, birthDate) VALUES (?, ?, ?)";

    @Override
    public List<User> findAllByFirstName(String firstName) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_NAME);
            statement.setString(1, firstName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                LocalDate birthDate = LocalDate.parse(resultSet.getString("birthDate"));

                User user = new User(id, firstName, password, birthDate);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean ifExist(String name, String password) {
        List<User> users = findAll();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //users = findAll();
        for (User user : users) {
            if (user.getName().equals(name)
                    && passwordEncoder.matches(password, user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<User> find(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                LocalDate birthDate = LocalDate.parse(resultSet.getString("birthDate"));

                return Optional.of(new User(id, name, password, birthDate));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User model) {

        PreparedStatement preparedStatement =
                null;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getPassword());
            preparedStatement.setString(3, model.getBirthDate().toString());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<User> findAll() {

        try {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                LocalDate birthDate = LocalDate.parse(resultSet.getString("birthDate"));

                User user = new User(id, name, password, birthDate);
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
