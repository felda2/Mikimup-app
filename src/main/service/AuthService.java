package main.service;

import main.dao.UserDAO;
import main.model.User;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }
}