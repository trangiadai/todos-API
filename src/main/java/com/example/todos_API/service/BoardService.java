package com.example.todos_API.service;
import com.example.todos_API.dto.request.BoardCreationRequest;
import com.example.todos_API.dto.request.BoardResponse;
import com.example.todos_API.dto.request.CardResponse;
import com.example.todos_API.dto.request.ColumnResponse;
import com.example.todos_API.entity.Board;
import com.example.todos_API.entity.Card;
import com.example.todos_API.entity.Column;
import com.example.todos_API.repository.BoardRepository;
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
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private CardRepository cardRepository;

    public Board createBoard(BoardCreationRequest request, String userId) {
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setDescription(request.getDescription());
        board.setType(request.getType());
        board.setCreatedAt(request.getCreatedAt());
        board.setUpdatedAt(request.getUpdatedAt());
        board.setColumnOrderIds(request.getColumnOrderIds());
        board.setDestroy(request.isDestroy());
        board.setUserID(userId);
        return boardRepository.save(board);
    }
    public List<Board> getBoard() {
        return boardRepository.findAll();
    }
    public Board getBoardById(ObjectId boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }
    public BoardResponse getBoardDetailById(ObjectId boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        // Lấy danh sách Column theo columnOrderIds
        List<ColumnResponse> columns = board.getColumnOrderIds().stream()
                .map(columnId -> {
                    Optional<Column> columnOpt = columnRepository.findById(columnId);
                    if (columnOpt.isPresent()) {
                        Column column = columnOpt.get();
                        // Lấy danh sách Card theo cardOrderIds
                        List<CardResponse> cards = column.getCardOrderIds().stream()
                                .map(cardId -> {
                                    Optional<Card> cardOpt = cardRepository.findById(cardId);
                                    return cardOpt.map(this::mapToCardDTO).orElse(null);
                                })
                                .filter(card -> card != null)
                                .collect(Collectors.toList());

                        // Map Column sang DTO
                        ColumnResponse columnDTO = new ColumnResponse();
                        columnDTO.set_id(column.get_id().toHexString()); // Chuyển ObjectId sang String
                        columnDTO.setTitle(column.getTitle());
                        columnDTO.setBoardId(column.getBoardId().toHexString()); // Chuyển ObjectId sang String
                        columnDTO.setCards(cards);
                        columnDTO.setCreatedAt(column.getCreatedAt());
                        columnDTO.setUpdatedAt(column.getUpdatedAt());
                        columnDTO.setDestroy(column.isDestroy());
                        return columnDTO;
                    }
                    return null;
                })
                .filter(column -> column != null)
                .collect(Collectors.toList());

        // Map Board sang DTO
        BoardResponse boardDTO = new BoardResponse();
        boardDTO.set_id(board.get_id().toHexString()); // Chuyển ObjectId sang String
        boardDTO.setTitle(board.getTitle());
        boardDTO.setDescription(board.getDescription());
        boardDTO.setType(board.getType());
        boardDTO.setColumns(columns);
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setUpdatedAt(board.getUpdatedAt());
        boardDTO.setDestroy(board.isDestroy());

        return boardDTO;
    }

    private CardResponse mapToCardDTO(Card card) {
        CardResponse cardDTO = new CardResponse();
        cardDTO.set_id(card.get_id().toHexString()); // Chuyển ObjectId sang String
        cardDTO.setTitle(card.getTitle());
        cardDTO.setColumnId(card.getColumnId().toHexString()); // Chuyển ObjectId sang String
        cardDTO.setBoardId(card.getBoardId().toHexString()); // Chuyển ObjectId sang String
        cardDTO.setCreatedAt(card.getCreatedAt());
        cardDTO.setUpdatedAt(card.getUpdatedAt());
        cardDTO.setDestroy(card.isDestroy());
        return cardDTO;
    }

    public Board pushColumnOrderIds(ObjectId boardId, ObjectId columnId) {
        Board board = getBoardById(boardId);

        if(board.getColumnOrderIds() == null) {
            board.setColumnOrderIds(new ArrayList<>());
        }
        if(!board.getColumnOrderIds().contains(columnId)) {
            board.getColumnOrderIds().add(columnId);
            board.setUpdatedAt(new Date());
            return boardRepository.save(board);
        }
        return board;
    }

    public Board updateColumnOrderIds(ObjectId boardId, List<ObjectId> columnOrder) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isEmpty()) {
            throw new RuntimeException("Board not found with ID: " + boardId);
        }
        Board board = optionalBoard.get();
        board.setColumnOrderIds(columnOrder);
        return boardRepository.save(board);
    }

    //Can viet lai method nay
    public BoardResponse updateBoard(ObjectId currentCardId, ObjectId prevColumnId,List<ObjectId> prevCardOrderIds, ObjectId nextColumnId ,List<ObjectId> nextCardOrderIds) {
        //tìm card thông qua id
        Optional<Card> optionalCard = cardRepository.findById(currentCardId);
        if (optionalCard.isEmpty()) {
            throw new RuntimeException("Card not found with ID: " + currentCardId);
        }
        // Sưa columnId trong card thành nextColumnId
        Card card = optionalCard.get();
        card.setColumnId(nextColumnId);
        card.setUpdatedAt(new Date());
        cardRepository.save(card);
//        Sua cardOderIds trong old Column
        Optional<Column> optionalPrevColumn = columnRepository.findById(prevColumnId);
        if (optionalPrevColumn.isEmpty()) {
            throw new RuntimeException("Old column not found with ID: " + prevColumnId);
        }
        Column prevColumn = optionalPrevColumn.get();
        prevColumn.setCardOrderIds(prevCardOrderIds);
        prevColumn.setUpdatedAt(new Date());
        columnRepository.save(prevColumn);
//        sua cardOderIds trong next Column
        Optional<Column> optionalNextColumn = columnRepository.findById(nextColumnId);
        if (optionalNextColumn.isEmpty()) {
            throw new RuntimeException("Next column not found with ID: " + nextColumnId);
        }
        Column nextColumn = optionalNextColumn.get();
        nextColumn.setCardOrderIds(nextCardOrderIds);
        nextColumn.setUpdatedAt(new Date());
        columnRepository.save(nextColumn);
        return getBoardDetailById(new ObjectId("67c5723c9c398522fc439c36"));
    }


    public void removeColumnOrderId(ObjectId boardId, ObjectId columnId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.getColumnOrderIds().remove(columnId);
            board.setUpdatedAt(new Date());
            boardRepository.save(board);
        }
    }

    public ObjectId getBoardByUserId(String userId){
        return boardRepository.findByUserID(userId).get_id();
    }

}


