package com.tms.easyrento.chat.repo;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-09-08 11:48
 */

public abstract class BaseMongoRepo {

    protected MongoTemplate mongoTemplate;

    protected BaseMongoRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected <T> List<T> aggregateToDto(Aggregation aggregation, String collection, Class<T> dtoClass) {
        return mongoTemplate.aggregate(aggregation, collection, dtoClass).getMappedResults();
    }

    protected <T> List<T> aggregateWithMapper(Aggregation aggregation,
                                              String collection,
                                              Function<Document, T> mapper) {
        List<Document> docs =
                mongoTemplate.aggregate(aggregation, collection, Document.class).getMappedResults();
        return docs.stream().map(mapper).toList();
    }

    protected <T> Optional<T> aggregateOne(Aggregation aggregation, String collection, Class<T> dtoClass) {
        List<T> results = mongoTemplate.aggregate(aggregation, collection, dtoClass).getMappedResults();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
