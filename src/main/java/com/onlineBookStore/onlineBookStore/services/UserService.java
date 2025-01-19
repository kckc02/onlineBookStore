package com.onlineBookStore.onlineBookStore.services;

import com.onlineBookStore.onlineBookStore.entities.User;

public interface UserService {
    User login(String username, String password);

    boolean register(User user);

    boolean updateProfile(int userId, String newUsername, String newPassword);

    void createAdmin(String username, String password);

}
