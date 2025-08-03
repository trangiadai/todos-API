package com.example.todos_API.controller;


import com.example.todos_API.dto.request.CardCreationRequest;
import com.example.todos_API.dto.request.CardResponse;
import com.example.todos_API.entity.Card;
import com.example.todos_API.service.CardService;
import com.example.todos_API.service.ColumnService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/cards")
@CrossOrigin(origins = "http://localhost:5173")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ColumnService columnService;

    @GetMapping
    public List<Card> getCard() {
        return cardService.getCard();
    }

    @GetMapping("/{cardId}")
    public Card getCardById(@PathVariable("cardId") ObjectId cardId) {
        return cardService.getCardById(cardId);
    }

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody Map<String, String> request) {
        try {
            String title = request.get("title");
            String columnIdStr = request.get("columnId");
            String boardIdStr = request.get("boardId");

            if (columnIdStr == null || columnIdStr.isBlank()) {
                throw new IllegalArgumentException("Column ID is required");
            }
            if (!ObjectId.isValid(columnIdStr)) {
                throw new IllegalArgumentException("Column ID must be a valid ObjectId");
            }

            if (boardIdStr == null || boardIdStr.isBlank()) {
                throw new IllegalArgumentException("Board ID is required");
            }
            if (!ObjectId.isValid(boardIdStr)) {
                throw new IllegalArgumentException("Board ID must be a valid ObjectId");
            }

            CardCreationRequest cardRequest = new CardCreationRequest();
            cardRequest.setTitle(title);
            cardRequest.setColumnId(new ObjectId(columnIdStr));
            cardRequest.setBoardId(new ObjectId(boardIdStr));
            cardRequest.setCreatedAt(new Date());

            // **GỌI HÀM ĐỂ LƯU VÀO DB**
            CardResponse newCard = cardService.createCard(cardRequest);

            return ResponseEntity.ok(newCard); // Trả về CardResponse thay vì Card
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/edit")
    public Card editCard(
            @RequestParam(required = false) String cardId,
            @RequestParam(required = false) String colunmId,
            @RequestParam String cardTitle,
            @RequestParam String colunmTitle
    ) {
        ObjectId cardIdObj = null;
        ObjectId colunmIdObj = null;

        if (cardId != null && !cardId.isEmpty()) {
            cardIdObj = new ObjectId(cardId);
            cardService.editCard(cardIdObj, cardTitle);
        }

        if (colunmId != null && !colunmId.isEmpty()) {
            colunmIdObj = new ObjectId(colunmId);
            columnService.editColunm(colunmTitle, colunmIdObj);
        }

        return cardIdObj != null ? cardService.getCardById(cardIdObj) : null;
    }

}
