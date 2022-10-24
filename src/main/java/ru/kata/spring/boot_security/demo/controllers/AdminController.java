package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admins")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping()
    public String showAdminPage(Model model, Principal principal) {

        model.addAttribute("users_list", userService.showAllUsers());
        model.addAttribute("user", userService.show(principal.getName()));
        model.addAttribute("roles", roleService.findAllRoles());
        return "admins/adminpage";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userService.show(id));
        return "admins/show";
    }

    @GetMapping("/new")
    public String newUser(Model model, Principal principal) {

        model.addAttribute("user", new User());
        model.addAttribute("admin", userService.show(principal.getName()));
        model.addAttribute("roles", roleService.findAllRoles());
        return "admins/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {

        userService.save(user);
        return "redirect:/admins";
    }

    @PutMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admins";
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//
//        model.addAttribute("user", userService.show(id));
//        model.addAttribute("roles", roleService.findAllRoles());
//        return "admins/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
//
//        userService.update(id, user);
//        return "redirect:/admins";
//    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        userService.delete(id);
        return "redirect:/admins";
    }
}
