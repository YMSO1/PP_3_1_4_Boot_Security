package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admins")
    public List<User> showAll() {
        List<User> userList = userService.showAllUsers();

        return userList;
    }

    @GetMapping("/admins/{id}")
    public User showUser(@PathVariable int id) {
        User user = userService.show(id);

        return user;
    }

    @PostMapping("/admins")
    public User addNew(@RequestBody User user) {
        userService.save(user);

        return user;
    }

    @PutMapping("/admins/{id}")
    public User edit(@RequestBody User user, @PathVariable int id) {

        userService.update(id, user);
        return user;
    }

    @DeleteMapping("/admins/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }
}
