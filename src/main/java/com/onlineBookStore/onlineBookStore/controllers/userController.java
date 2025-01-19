package com.onlineBookStore.onlineBookStore.controllers;

import com.onlineBookStore.onlineBookStore.entities.User;
import com.onlineBookStore.onlineBookStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private UserService userService;

    // Endpoint to login a user
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user == null) {
            return ResponseEntity.status(401).body(null); // Unauthorized if user not found
        }
        return ResponseEntity.ok(user); // Return the user details on successful login
    }

    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        boolean isRegistered = userService.register(user);
        if (!isRegistered) {
            return ResponseEntity.status(400).body("Username already exists."); // Bad request if username exists
        }
        return ResponseEntity.ok("User registered successfully.");
    }

    // Endpoint to update user profile
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateProfile(
            @PathVariable int userId,
            @RequestParam(required = false) String newUsername,
            @RequestParam(required = false) String newPassword) {

        boolean isUpdated = userService.updateProfile(userId, newUsername, newPassword);
        if (!isUpdated) {
            return ResponseEntity.status(404).body("User not found."); // Not found if user doesn't exist
        }
        return ResponseEntity.ok("Profile updated successfully.");
    }

    // Endpoint to create an admin user (for testing or specific purposes)
    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestParam String username, @RequestParam String password) {
        userService.createAdmin(username, password);
        return ResponseEntity.ok("Admin created successfully.");
    }
}
