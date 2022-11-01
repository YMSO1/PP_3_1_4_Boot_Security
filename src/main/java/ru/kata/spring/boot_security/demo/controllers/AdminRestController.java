package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> showAll() {

        return userService.showAllUsers();
    }

    @GetMapping("/{id}")
    public User showUser(@PathVariable int id) {

        return userService.show(id);
    }

    @PostMapping()
    public User addNew(@RequestBody User user) {

        userService.save(user);
        return user;
    }

    @PutMapping("/{id}")
    public User edit(@RequestBody User user, @PathVariable int id) {

        userService.update(id, user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {

        userService.delete(id);
    }
}
