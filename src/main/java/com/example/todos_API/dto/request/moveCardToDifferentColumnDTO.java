package com.example.todos_API.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class moveCardToDifferentColumnDTO {
    private String currentCardId;
    private String prevColumnId;
    private List<String> prevCardOrderIds;
    private String nextColumnId;
    private List<String> nextCardOrderIds;
}
