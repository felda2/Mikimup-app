package main.service;

import main.model.User;

public class AuthService {

    public User login(String username, String password) {
        if (username.equals("admin") && password.equals("123")) {
            return new User("admin", "123", "admin");
        }

        return null;
    }
}