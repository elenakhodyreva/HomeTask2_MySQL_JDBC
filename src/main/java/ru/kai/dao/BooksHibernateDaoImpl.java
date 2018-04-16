package ru.kai.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kai.models.Book;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BooksHibernateDaoImpl implements BooksDao {

    Configuration configuration = new Configuration();
    SessionFactory sessionFactory;
    Session session;

    public BooksHibernateDaoImpl(Configuration configuration) {

        this.configuration= configuration;
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
    }


    @Override
    public Optional<Book> find(Integer id) {
        return Optional.empty();
    }

    @Override
    public void save(Book model) {

        session.beginTransaction();
        session.save(new Book(model.getName(), model.getAuthor(), model.getCount(), model.getCost()));
        session.getTransaction().commit();
    }

    @Override
    public void update(Book model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Book> findAll() {
        List<Book> books = session.createQuery("from Book book", Book.class).getResultList();
        return books;
    }
}
