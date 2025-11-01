package com.tms.easyrento.chat.repo;

import com.tms.easyrento.chat.dto.ChatResponseDto;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.DateOperators;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-09-08 11:49
 */

@Repository
public class ConversationRepo extends BaseMongoRepo {

    protected ConversationRepo(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public List<ChatResponseDto> findPrivateMessagesForUser(Long userId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(
                        Criteria.where("message_type").is("PRIVATE")
                                .orOperator(
                                        Criteria.where("sender_id").is(userId),
                                        Criteria.where("receiver_id").is(userId)
                                )
                ),
                lookup("users", "sender_id", "user_id", "sender"),
                Aggregation.unwind("sender"),
                lookup("users", "receiver_id", "user_id", "receiver"),
                Aggregation.unwind("receiver"),
                Aggregation.project()
                        .and(ConvertOperators.ToString.toString(Aggregation.fields("_id"))).as("id")
                        .and("content").as("content")
                        .and(DateOperators.DateToString.dateOf("time_stamp")
                                .toString("%Y-%m-%dT%H:%M:%S.%LZ")
                                .withTimezone(DateOperators.Timezone.valueOf("+00:00")))
                        .as("time_stamp")
                        .and(ConditionalOperators.ifNull("message_type").then("PRIVATE")).as("type")
                        .and("edited").as("edited")
                        .and("sender").as("sender")
                        .and("receiver").as("receiver"),
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "time_stamp"))
        );

        return aggregateWithMapper(aggregation, "conversations", d -> {
            ChatResponseDto dto = new ChatResponseDto();
            dto.setId(d.get("id", String.class));
            dto.setContent(d.get("content", String.class));
            dto.setTimestamp(d.get("time_stamp", String.class));
            dto.setMessageType(d.get("type", String.class));
            dto.setEdited(Boolean.TRUE.equals(d.get("edited", Boolean.class)));

            Document senderDoc = d.get("sender", Document.class);
            if (senderDoc != null) {
                dto.setSenderId(String.valueOf(senderDoc.get("user_id")));
                dto.setSenderName(senderDoc.get("name", String.class));
            }

            Document receiverDoc = d.get("receiver", Document.class);
            if (receiverDoc != null) {
                dto.setReceiverId(String.valueOf( receiverDoc.get("user_id")));
                dto.setReceiverName(receiverDoc.get("name", String.class));
            }

            return dto;
        });
    }
}
