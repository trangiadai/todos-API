package com.example.todos_API.dto.request;

import lombok.Data;

@Data
public class UserCreationRequest {
    private String username;
    private String password;
}
