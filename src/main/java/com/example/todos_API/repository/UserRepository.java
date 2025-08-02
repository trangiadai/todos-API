package com.example.todos_API.repository;

import com.example.todos_API.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);


    boolean existsByUsername(String s);
}
