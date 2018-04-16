package ru.kai.models;

import java.time.LocalDate;

public class User {
    private Integer id;
    private String name;
    private String password;
    private LocalDate birthDate;

    public User() {
    }

    public User(Integer id, String name, String password, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
    }

    public User(String name, String password, LocalDate birthDate) {
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
