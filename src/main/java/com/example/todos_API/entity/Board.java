package com.example.todos_API.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "boards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    private ObjectId _id;
    private String userID;
    private String title;
    private String description;
    private String type;
    private List<ObjectId> columnOrderIds;
    private Date createdAt = new Date();
    private Date updatedAt;
    private boolean destroy;
}
