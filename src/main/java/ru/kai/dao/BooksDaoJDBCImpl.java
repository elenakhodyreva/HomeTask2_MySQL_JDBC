package ru.kai.dao;

import ru.kai.models.Book;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BooksDaoJDBCImpl implements BooksDao {

    private Connection connection;
    public BooksDaoJDBCImpl(DataSource dataSource){
        try {
            this.connection= dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    //language=SQL
    private final String SQL_SELECT_ALL=
            "SELECT * FROM book";

    //language=SQL
    private final String SQL_SELECT_BY_ID=
            "SELECT * FROM book WHERE id=? ";

    //language=SQL
    private final String SQL_INSERT_BOOK=
            "INSERT INTO book (name, author, count, cost) VALUES (?, ?, ?, ?)";


    @Override
    public Optional<Book> find(Integer id) {
        try {
            PreparedStatement statement= connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet= statement.executeQuery();
            if (resultSet.next()){
                String name= resultSet.getString("name");
                String author= resultSet.getString("author");
                Integer count= resultSet.getInt("count");
                Integer cost= resultSet.getInt("cost");

                return Optional.of(new Book(name, author, count, cost));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Book model) {

        PreparedStatement preparedStatement =
                null;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getAuthor());
            preparedStatement.setInt(3, model.getCount());
            preparedStatement.setInt(4, model.getCost());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void update(Book model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Book> findAll() {

        try {
            List<Book> books= new ArrayList<>();
            Statement statement= connection.createStatement();
            ResultSet resultSet= statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()){
                String name= resultSet.getString("name");
                String author= resultSet.getString("author");
                Integer count= resultSet.getInt("count");
                Integer cost= resultSet.getInt("cost");

                Book user= new Book(name, author, count, cost);
                books.add(user);
            }

            return books;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
