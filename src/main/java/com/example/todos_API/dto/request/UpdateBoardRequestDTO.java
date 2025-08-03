package com.example.todos_API.dto.request;

import lombok.Data;

import java.util.List;

@Data
// because
// JSON từ request body gửi lên luôn luôn có cấu trúc của một object, nêu phải làm 1 cái DTO để truy xất vào ừng giá trị, không thể access trực tiếp từ controller
public class UpdateBoardRequestDTO {
    private List<String> columnOrderIds;
}
