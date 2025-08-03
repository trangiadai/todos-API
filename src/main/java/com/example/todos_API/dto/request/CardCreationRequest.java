package com.example.todos_API.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Data
public class CardCreationRequest {
    @NotNull(message = "Title is required")
    @Size(min = 1, message = "Title cannot be empty")
    private String title;
    @NotNull(message = "Column ID is required")
    private ObjectId columnId;
    @NotNull(message = "Board ID is required")
    private ObjectId boardId;
    private Date createdAt = new Date();
    private Date updatedAt;
    private boolean destroy;
}
