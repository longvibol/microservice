package com.piseth.card.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.piseth.card.entity.Card;

public interface CardRepository extends MongoRepository<Card, Long>{

}
