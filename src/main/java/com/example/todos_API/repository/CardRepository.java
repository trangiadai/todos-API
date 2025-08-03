package com.example.todos_API.repository;

import com.example.todos_API.entity.Card;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends MongoRepository<Card, ObjectId> {
    void deleteAllByColumnId(ObjectId columnId);
}
