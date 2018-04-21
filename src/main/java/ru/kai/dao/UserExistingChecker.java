package ru.kai.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kai.models.User;

import java.util.List;

public class UserExistingChecker {

    //password encoder -interface
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private List<User> users;

    public UserExistingChecker(List<User> users) {
        this.users = users;
    }

    public boolean isExist(String name, String password){
            for (User user : users) {
                if (user.getName().equals(name)
                        && passwordEncoder.matches(password, user.getPassword())) {
                    return true;
                }
            }
            return false;
    }
}
