package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> showAllUsers();

    User show(int id);

    User show(String name);

    User save(User user);

    void delete(int id);

    User update(int id, User user);
}
