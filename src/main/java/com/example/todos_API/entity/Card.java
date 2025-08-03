package com.example.todos_API.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    private ObjectId _id;
    private String title;
    private ObjectId columnId;
    private ObjectId boardId;
    private Date createdAt = new Date();
    private Date updatedAt;
    private boolean destroy;
}
