package ru.kai.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.kai.models.Book;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BooksHibernateDaoImpl implements BooksDao {

    Configuration configuration;
    SessionFactory sessionFactory;
    Session session;
    Query query;
    int result;

    public BooksHibernateDaoImpl(Configuration configuration) {

        this.configuration = configuration;
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
    }

    @Override
    public Optional<Book> find(Integer id) {
        Session session = sessionFactory.openSession();
        query = session.createQuery("from Book where id=:id");
        query.setParameter("id", id);
        Book book = (Book) query.getSingleResult();

        //Hibernates uniqueResult() method returns null if no data was found
        if (query.uniqueResult() != null)
            return Optional.of(book);
        else
            return Optional.empty();
    }

    @Override
    public void save(Book model) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new Book(model.getName(), model.getAuthor(), model.getCount(), model.getCost()));
        session.getTransaction().commit();
    }

    @Override
    public void update(Book model) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(model);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(session.load(Book.class, id));
        session.getTransaction().commit();
    }

    @Override
    public List<Book> findAll() {

        Session session = sessionFactory.openSession();
        List<Book> books = session.createQuery("from Book book", Book.class).getResultList();
        return books;
    }

    @Override
    public Book findByFirstName(String name) {
        Session session = sessionFactory.openSession();
        query = session.createQuery("from Book where name=:name");
        query.setParameter("name", name);
        Book book = (Book) query.uniqueResult();
        return book;
    }
}
