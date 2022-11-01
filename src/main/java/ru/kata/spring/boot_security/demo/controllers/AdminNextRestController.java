package ru.kata.spring.boot_security.demo.controllers;

//@RestController
//@RequestMapping("/api/users")
////@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//public class AdminRestController {
//
//    private UserService userService;
//
//    @Autowired
//    public AdminRestController(UserService userService) {
//        this.userService = userService;
//    }
//
//    public AdminRestController() {
//    }
//
//
//    @GetMapping
//    public ResponseEntity<List<User>> getAll() {
//        List<User> usersList= userService.showAllUsers();
//        return new ResponseEntity<>(usersList, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<User> addUser(@RequestBody User user) {
//        return ResponseEntity.ok(userService.save(user));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById (@PathVariable ("id") int id) {
//        return ResponseEntity.ok(userService.show(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<User> editUser(@RequestBody User user, @PathVariable ("id") int id)
//    {
//        return ResponseEntity.ok(userService.update(id, user));
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUserById(@PathVariable("id") int id) {
//        userService.delete(id);
//    }
//
//
//}