package com.example.todos_API.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ColumnCreationRequest {
    @NotNull(message = "Title is required")
    @Size(min = 1, message = "Title cannot be empty")
    private String title;
    @NotNull(message = "Board ID is required")
    private ObjectId boardId;
    private List<ObjectId> cardOrderIds = new ArrayList<>();
//    in postman
    //khong duoc tạo data thieu "cardOrderIds": [], it nhat cung set nó là [], nếu k khi server chạy sẽ bị lỗi
    // nhung minh bo no vì minh đã khoi tạo mặc định trong back end là []
    private Date createdAt = new Date();
    private Date updatedAt;
    private boolean destroy;
}
