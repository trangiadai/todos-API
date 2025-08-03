package com.example.todos_API.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

@Data
public class ColumnResponse {
//    private ObjectId _id;
    private String _id;
    private String title;
//    private ObjectId boardId;
    private String boardId;
    private List<CardResponse> cards; // Lấy danh sách Card chi tiết
    private Date createdAt;
    private Date updatedAt;
    private boolean destroy;
}
