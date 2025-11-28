package com.travelingjournal.service;

import com.travelingjournal.dao.UserDAO;
import com.travelingjournal.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Register a new user with BCrypt password hashing
     */
    public User registerUser(String name, String email, String plainPassword) {
        if (name == null || email == null || plainPassword == null ||
            name.trim().isEmpty() || email.trim().isEmpty() || plainPassword.length() < 6) {
            return null;
        }

        String passwordHash = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
        User user = new User(name.trim(), email.toLowerCase().trim(), passwordHash);

        Long id = userDAO.save(user);
        if (id != null) {
            user.setId(id);
            return user;
        }
        return null;
    }

    /**
     * Authenticate user by email and password
     */
    public User authenticate(String email, String plainPassword) {
        if (email == null || plainPassword == null) return null;

        User user = userDAO.findByEmail(email.toLowerCase().trim());
        if (user != null && BCrypt.checkpw(plainPassword, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    /**
     * Check if email already exists
     */
    public boolean emailExists(String email) {
        return userDAO.findByEmail(email.toLowerCase().trim()) != null;
    }

    /**
     * Get user by ID (useful for profile, etc.)
     */
    public User getUserById(Long id) {
        return userDAO.findById(id);
    }
}