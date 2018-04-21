package ru.kai.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kai.models.User;

import java.util.List;
import java.util.Optional;

public class IndependentUserDAO {

    @Autowired
    private UsersDao someUsersDao;

    public IndependentUserDAO() {
        //this.someUsersDao = someUsersDao;
    }

    public List<User> findAllByFirstName(String firstName) {
        return someUsersDao.findAllByFirstName(firstName);
    }

    public Optional<User> find(Integer id) {
        return someUsersDao.find(id);
    }

    public void save(User model) {
        someUsersDao.save(model);
    }

    public void update(User model) {

    }

    public void delete(Integer id) {

    }

    public List<User> findAll() {
        return someUsersDao.findAll();
    }
}
