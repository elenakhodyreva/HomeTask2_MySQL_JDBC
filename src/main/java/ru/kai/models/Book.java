package ru.kai.models;

public class Book {
    private Integer id;
    private String name;
    private String author;
    private Integer count;
    private Integer cost;

    public Book() {
    }

    public Book(String name, String author, int count, int cost) {
        this.name = name;
        this.author = author;
        this.count = count;
        this.cost = cost;
    }

    public Book(Integer id, String name, String author, Integer count, Integer cost) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.count = count;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
