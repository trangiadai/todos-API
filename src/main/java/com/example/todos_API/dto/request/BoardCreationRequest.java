package com.example.todos_API.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class BoardCreationRequest {
    @NotNull(message = "Title is required")
    @Size(min = 1, message = "Title cannot be empty")
    private String title;
    private String description;
    private String type;
    private List<ObjectId> columnOrderIds = new ArrayList<>();
    private Date createdAt = new Date();
    private Date updatedAt;
    private boolean destroy;
}
