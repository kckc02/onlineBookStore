package com.onlineBookStore.onlineBookStore.implementations;

import com.onlineBookStore.onlineBookStore.entities.User;
import com.onlineBookStore.onlineBookStore.repositories.UserRepository;
import com.onlineBookStore.onlineBookStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public boolean register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false; // Username already exists
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateProfile(int userId, String newUsername, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!newUsername.isEmpty()) {
                user.setUsername(newUsername);
            }
            if (!newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }
            userRepository.save(user);
            return true;
        }
        return false; // User not found
    }

    @Override
    public void createAdmin(String username, String password) {
        User admin = new User();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setRole("admin"); // Explicitly set the role as 'admin'
        userRepository.save(admin);
    }
}
