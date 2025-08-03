package com.example.todos_API.controller;

import com.example.todos_API.dto.request.UserCreationRequest;
import com.example.todos_API.entity.User;
import com.example.todos_API.repository.UserRepository;
import com.example.todos_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired // ✅ THÊM DÒNG NÀY
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestBody UserCreationRequest user) {
        return userService.Login(user);
    }

    @PostMapping("/signup")
    public User signup(@RequestBody UserCreationRequest req) {
        return userService.Create(req);
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");

        if (userRepository.existsByUsername(email)) {
            return ResponseEntity.ok("User đã tồn tại");
        }

        UserCreationRequest req = new UserCreationRequest();
        req.setUsername(email);
        req.setPassword("1234");

        userService.Create(req);
        return ResponseEntity.ok("Tạo user thành công");
    }
}

