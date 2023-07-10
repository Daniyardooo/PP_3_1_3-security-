package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);

    void updateUserById(Long id, User updatedUser);

    void saveUser(User user);

    List<User> getAllUsers();

    void deleteUserById(Long userId);

    User findByUsername(String username);


}
