package com.example.todos_API.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateColumnRequestDTO {
    private List<String> cardOrderIds;
}
