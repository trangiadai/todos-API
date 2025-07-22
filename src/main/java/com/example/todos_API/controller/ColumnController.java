package com.example.todos_API.controller;


import com.example.todos_API.dto.request.ColumnCreationRequest;
import com.example.todos_API.dto.request.ColumnResponse;
import com.example.todos_API.dto.request.UpdateBoardRequestDTO;
import com.example.todos_API.dto.request.UpdateColumnRequestDTO;
import com.example.todos_API.entity.Board;
import com.example.todos_API.entity.Column;
import com.example.todos_API.service.ColumnService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/columns")
@CrossOrigin(origins = "http://localhost:5173")
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    @GetMapping
    public List<Column> getColumn() {
        return columnService.getColumn();
    }

//    @GetMapping("/{columnId}")
//    public Column getColumnById(@PathVariable("columnId") String columnId) {
//        return columnService.getColumnById(new ObjectId(columnId));
//    }

    @GetMapping("/{columnId}")
    public ColumnResponse getColumnById(@PathVariable("columnId") String columnId) {
        return columnService.getColumnByIdResponse(new ObjectId(columnId));
    }


    @PutMapping("/{columnId}")
    public ResponseEntity<?> updateCardOrder(
            @PathVariable("columnId") String columnId,
            @RequestBody UpdateColumnRequestDTO request) {
//        System.out.println("Received columnId from path: " + columnId);
//        System.out.println("Received cardOrderIds: " + request.getCardOrderIds());
        List<ObjectId> cardOrder = request.getCardOrderIds().stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());

        Column updatedColumn = columnService.updateCardOrderIds(new ObjectId(columnId), cardOrder);
        return ResponseEntity.ok(updatedColumn);
    }



//    @PostMapping
//    public Column createColumn(@RequestBody ColumnCreationRequest request) {
//        Column newColumn = columnService.createColumn(request);
//        return getColumnById(newColumn.get_id().toHexString());
//    }
    @PostMapping
    public ColumnResponse createColumn(@RequestBody ColumnCreationRequest request) {
        Column newColumn = columnService.createColumn(request);
        return columnService.convertToColumnResponse(newColumn);
    }



    @DeleteMapping("/{columnId}")
    public ResponseEntity<?> deleteColumn( @PathVariable("columnId") String columnId) {
//        Column deletedColumn = columnService.deleteColumn(new ObjectId(columnId));
//        return ResponseEntity.ok(deletedColumn);
        ObjectId objectId;
        try {
            objectId = new ObjectId(columnId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid ObjectId format: " + columnId);
        }

        Column deletedColumn = columnService.deleteColumn(objectId);
        return ResponseEntity.ok(deletedColumn);
    }
}
