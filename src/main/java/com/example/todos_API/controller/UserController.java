package com.example.todos_API.controller;

import com.example.todos_API.dto.request.UserCreationRequest;
import com.example.todos_API.entity.User;
import com.example.todos_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserCreationRequest user){
        return userService.Login(user);
    }

    @PostMapping("/signup")
    public User signup(@RequestBody UserCreationRequest req){
        return userService.Create(req);
    }
}
