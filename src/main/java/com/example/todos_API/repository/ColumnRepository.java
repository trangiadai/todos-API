package com.example.todos_API.repository;

import com.example.todos_API.entity.Column;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends MongoRepository<Column, ObjectId> {
}
