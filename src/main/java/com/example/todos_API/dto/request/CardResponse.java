package com.example.todos_API.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
public class CardResponse {
//    private ObjectId _id;
    private String _id;
    private String title;
//    private ObjectId columnId;
//    private ObjectId boardId;
    private String columnId;
    private String boardId;
    private Date createdAt;
    private Date updatedAt;
    private boolean destroy;
}
