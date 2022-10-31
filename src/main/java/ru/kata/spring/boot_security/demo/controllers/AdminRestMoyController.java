package ru.kata.spring.boot_security.demo.controllers;

//@RestController
//@RequestMapping("/api")
//public class AdminRestControllerMoy {
//    private final UserService userService;
//
//    @Autowired
//    public AdminRestControllerMoy(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/admins")
//    public List<User> showAll() {
//        List<User> userList = userService.showAllUsers();
//
//        return userList;
//    }
//
//    @GetMapping("/admins/{id}")
//    public User showUser(@PathVariable int id) {
//        User user = userService.show(id);
//
//        return user;
//    }
//
//    @PostMapping("/admins")
//    public User addNew(@RequestBody User user) {
//        userService.save(user);
//
//        return user;
//    }
//
//    @PutMapping("/admins/{id}")
//    public User edit(@RequestBody User user, @PathVariable int id) {
//
//        userService.update(id, user);
//        return user;
//    }
//
//    @DeleteMapping("/admins/{id}")
//    public void delete(@PathVariable int id) {
//        userService.delete(id);
//    }
//}
