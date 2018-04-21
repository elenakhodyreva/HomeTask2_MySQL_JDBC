package ru.kai.dao;

import ru.kai.models.Book;

import java.util.List;

public interface BooksDao extends CrudDao<Book> {
    Book findByFirstName(String name);
}

