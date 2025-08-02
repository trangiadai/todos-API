package com.example.todos_API.controller;
import com.example.todos_API.dto.request.BoardResponse;
import com.example.todos_API.dto.request.UpdateBoardRequestDTO;
import com.example.todos_API.dto.request.moveCardToDifferentColumnDTO;
import com.example.todos_API.entity.Board;
import com.example.todos_API.service.BoardService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/boards")
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/{boardId}/details")
    public BoardResponse getBoardDetailById(@PathVariable("boardId") ObjectId boardId) {
        return boardService.getBoardDetailById(boardId);
    }

    @GetMapping
    public List<Board> getBoard() {
        return boardService.getBoard();
    }

    @GetMapping("/{boardId}")
    public BoardResponse getBoardById(@PathVariable("boardId") String boardId) {
        return boardService.getBoardDetailById(new ObjectId(boardId)); // Chuyển String thành ObjectId khi nhận request
    }

//    @PostMapping
//    public Board createBoard(@RequestBody BoardCreationRequest request) {
//        Board newBoard = boardService.createBoard(request);
//        return getBoardById(newBoard.get_id());
//    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> updateColumnOrder(
            @PathVariable("boardId") String boardId,
            @RequestBody UpdateBoardRequestDTO request) {
//        System.out.println("Received boardId from path: " + boardId);
//        System.out.println("Received columnOrderIds: " + request.getColumnOrderIds());
        List<ObjectId> columnOrder = request.getColumnOrderIds().stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());

        Board updatedBoard = boardService.updateColumnOrderIds(new ObjectId(boardId), columnOrder);
        return ResponseEntity.ok(updatedBoard);
    }

    @PutMapping("/supports/moving_card")
    public ResponseEntity<?> moveCardToDifferentColumn(
            @RequestBody moveCardToDifferentColumnDTO request) {
        List<ObjectId> preCardOrder = request.getPrevCardOrderIds().stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());
        List<ObjectId> nextCardOrder = request.getNextCardOrderIds().stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());
        BoardResponse updatedBoard = boardService.updateBoard(new ObjectId(request.getCurrentCardId()),new ObjectId(request.getPrevColumnId()), preCardOrder, new ObjectId(request.getNextColumnId()), nextCardOrder);
        return ResponseEntity.ok(updatedBoard);
    }

    @GetMapping("/getBoard/{userId}")
    public BoardResponse getBoardByUserId(@PathVariable String userId){
        ObjectId boardId = boardService.getBoardByUserId(userId);
        return boardService.getBoardDetailById(boardId);
    }
}
