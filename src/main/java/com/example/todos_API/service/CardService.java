package com.example.todos_API.service;


import com.example.todos_API.dto.request.CardCreationRequest;
import com.example.todos_API.dto.request.CardResponse;
import com.example.todos_API.entity.Card;
import com.example.todos_API.repository.CardRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ColumnService columnService;

    public CardResponse createCard(CardCreationRequest request) {
        Card newCard = new Card();
        newCard.setTitle(request.getTitle());
        newCard.setColumnId(request.getColumnId());
        newCard.setBoardId(request.getBoardId());
        newCard.setCreatedAt(request.getCreatedAt());
        newCard.setUpdatedAt(request.getUpdatedAt());
        newCard.setDestroy(request.isDestroy());

        Card savedCard = cardRepository.save(newCard);
        columnService.pushCardOrderIds(savedCard.getColumnId(), savedCard.get_id());

        // Chuyển đổi sang CardResponse để trả về kiểu String
        CardResponse cardResponAfterSaved = new CardResponse();
        cardResponAfterSaved.set_id(savedCard.get_id().toHexString());
        cardResponAfterSaved.setTitle(savedCard.getTitle());
        cardResponAfterSaved.setColumnId(savedCard.getColumnId().toHexString());
        cardResponAfterSaved.setBoardId(savedCard.getBoardId().toHexString());
        cardResponAfterSaved.setCreatedAt(savedCard.getCreatedAt());
        cardResponAfterSaved.setUpdatedAt(savedCard.getUpdatedAt());
        cardResponAfterSaved.setDestroy(savedCard.isDestroy());
        return cardResponAfterSaved;
    }

    public List<Card> getCard() {
        return cardRepository.findAll();
    }

    public Card getCardById(ObjectId cardId){
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public Card editCard(ObjectId cardId, String title) {
        Card card = getCardById(cardId);
        card.setTitle(title);
        return cardRepository.save(card);
    }

}
