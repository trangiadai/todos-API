package com.example.todos_API.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "columns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Column {
    @Id
    private ObjectId _id;
    private String title;
    private ObjectId boardId;
    private List<ObjectId> cardOrderIds;
    private Date createdAt = new Date();
    private Date updatedAt;
    private boolean destroy;
}

