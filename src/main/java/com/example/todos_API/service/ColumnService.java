package com.example.todos_API.service;

import com.example.todos_API.dto.request.CardResponse;
import com.example.todos_API.dto.request.ColumnCreationRequest;
import com.example.todos_API.dto.request.ColumnResponse;
import com.example.todos_API.entity.Board;
import com.example.todos_API.entity.Column;
import com.example.todos_API.repository.CardRepository;
import com.example.todos_API.repository.ColumnRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColumnService {
    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CardRepository cardRepository;

    public Column createColumn(ColumnCreationRequest request) {
        Column column = new Column();
        column.setTitle(request.getTitle());
        column.setBoardId(request.getBoardId());
        column.setCardOrderIds(request.getCardOrderIds());
        column.setCreatedAt(request.getCreatedAt());
        column.setUpdatedAt(request.getUpdatedAt());
        column.setDestroy(request.isDestroy());
        Column savedColumn = columnRepository.save(column);
        boardService.pushColumnOrderIds(request.getBoardId(), savedColumn.get_id());
        return savedColumn;
    }
    public List<Column> getColumn() {
        return columnRepository.findAll();
    }

    public Column getColumnById(ObjectId columnId){
        return columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
    }

    public ColumnResponse convertToColumnResponse(Column column) {
        ColumnResponse response = new ColumnResponse();
        response.set_id(column.get_id().toHexString());
        response.setTitle(column.getTitle());
        response.setBoardId(column.getBoardId().toHexString());
        response.setCreatedAt(column.getCreatedAt());
        response.setUpdatedAt(column.getUpdatedAt());
        response.setDestroy(column.isDestroy());

        if (column.getCardOrderIds() != null) {
            response.setCards(column.getCardOrderIds().stream()
                    .map(cardId -> {
                        CardResponse cardResponse = new CardResponse();
                        cardResponse.set_id(cardId.toHexString());
                        return cardResponse;
                    })
                    .collect(Collectors.toList()));
        }

        return response;
    }

    public ColumnResponse getColumnByIdResponse(ObjectId columnId) {
        Column column = getColumnById(columnId);
        return convertToColumnResponse(column);
    }




    public Column pushCardOrderIds(ObjectId columnId, ObjectId cardId) {
        Column column = getColumnById(columnId);

        if(column.getCardOrderIds() == null) {
            column.setCardOrderIds(new ArrayList<>());
        }
        if(!column.getCardOrderIds().contains(cardId)) {
            column.getCardOrderIds().add(cardId);
            column.setUpdatedAt(new Date());
            return columnRepository.save(column);
        }
        return column;
    }

    public Column updateCardOrderIds(ObjectId columnId, List<ObjectId> newCardOrder) {
        Column column = getColumnById(columnId);
        column.setCardOrderIds(newCardOrder);
        column.setUpdatedAt(new Date());
        return columnRepository.save(column);
    }

    public Column deleteColumn(ObjectId columnId) {
        System.out.println("Trying to delete column with ID: " + columnId);
        Optional<Column> optionalColumn = columnRepository.findById(columnId);
        if (optionalColumn.isEmpty()) {
            throw new RuntimeException("Column not found with ID: " + columnId);
        }
        Column column = optionalColumn.get();
        // Step 1: Remove columnId from the board's columnOrderIds
        boardService.removeColumnOrderId(column.getBoardId(), columnId);

        // Step 2: Delete all cards that belong to this column
        cardRepository.deleteAllByColumnId(columnId);

        // Step 3: Delete the column itself
        columnRepository.deleteById(columnId);
        return column;
    }

}
