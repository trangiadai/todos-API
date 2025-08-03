package com.example.todos_API.dto.request;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class BoardResponse {
    private String _id; // Chuyển từ ObjectId sang String
    private String title;
    private String description;
    private String type;
    private List<ColumnResponse> columns; // Lấy danh sách Column chi tiết
    private Date createdAt;
    private Date updatedAt;
    private boolean destroy;
}
