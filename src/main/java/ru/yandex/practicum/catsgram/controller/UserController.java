package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;
import java.util.Optional;

@RestController
public class UserController {

    UserService userService;

    @GetMapping("/users/{userEmail}")
    public Optional<User> findById(@PathVariable String userEmail) {
        return Optional.ofNullable(userService.findUserByEmail(userEmail));
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @PostMapping(value = "/user")
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping(value = "/user")
    public User put(@RequestBody User user) {
        return userService.put(user);
    }
}