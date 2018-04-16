package ru.kai.dao;

import ru.kai.models.User;

import java.util.List;

public interface UsersDao extends CrudDao<User> {
    List<User> findAllByFirstName(String firstName);
    boolean ifExist(String name, String password);
}
