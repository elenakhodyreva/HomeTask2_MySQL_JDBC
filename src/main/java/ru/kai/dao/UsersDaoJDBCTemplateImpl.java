package ru.kai.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kai.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDaoJDBCTemplateImpl implements UsersDao {

    private JdbcTemplate template;

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM fix_user";
    //language=SQL
    private final String SQL_FIND_BY_NAME =
            "SELECT * from fix_user WHERE name=?";

    //language=SQL
    private final String SQL_INSERT_USER =
            "INSERT INTO fix_user (name, password, birthdate) VALUES (?, ?, ?)";

    //language=SQL
    private final String SQL_UPDATE_USER =
            "UPDATE fix_user SET name=?, password=? WHERE id=?";

    //language=SQL
    private final String SQL_DELETE_USER =
            "DELETE FROM fix_user WHERE id=?";

    public UsersDaoJDBCTemplateImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (resultSet, i) -> {

        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("password"),
                LocalDate.parse(resultSet.getString("birthDate"))
        );
    };

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return template.query(SQL_FIND_BY_NAME, userRowMapper, firstName);
    }

    @Override
    public Optional<User> find(Integer id) {
        return Optional.empty();
    }

    @Override
    public void save(User model) {

        String name = model.getName();
        String password = model.getPassword();
        String birthDate = model.getBirthDate().toString();
        template.update(SQL_INSERT_USER, name, password, birthDate);
    }

    @Override
    public void update(User model) {

        template.update(SQL_UPDATE_USER, model.getName(), model.getPassword(), model.getId());
    }

    @Override
    public void delete(Integer id) {

        template.update(SQL_DELETE_USER, id);
    }

    @Override
    public List<User> findAll() {
        return template.query(SQL_SELECT_ALL, userRowMapper);
    }
}
