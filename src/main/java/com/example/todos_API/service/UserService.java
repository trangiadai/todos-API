package com.example.todos_API.service;

import com.example.todos_API.dto.request.BoardCreationRequest;
import com.example.todos_API.dto.request.UserCreationRequest;
import com.example.todos_API.entity.Board;
import com.example.todos_API.entity.User;
import com.example.todos_API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepo;
    @Autowired
    public BoardService boardService;
    public User Create(UserCreationRequest req){
        User user = new User();
//        if(userRepo.existsByUsername(req.getUsername())){
//            throw new RuntimeException("user name exist");
//        }
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        System.out.println(req);
        System.out.println(user);
        BoardCreationRequest board = new BoardCreationRequest();

       User u = userRepo.save(user);
        boardService.createBoard(board, u.getId());
        return u;
    }

    public String Login(UserCreationRequest req) {
        User user = userRepo.findByUsername(req.getUsername());
        if(user.getPassword().equals(req.getPassword())){
            return user.getId();
        } else return null;
    }
}
