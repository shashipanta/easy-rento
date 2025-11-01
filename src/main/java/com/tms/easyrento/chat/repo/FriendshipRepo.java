package com.tms.easyrento.chat.repo;

import com.tms.easyrento.chat.dto.FriendshipDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class FriendshipRepo extends BaseMongoRepo{
    protected FriendshipRepo(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public List<FriendshipDto> getAllFriends(Long userId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(
                        Criteria.where("user_id").is(userId)
                ),
                lookup("users", "friend_id", "user_id", "friend"),
                unwind("friend"),
                project()
                        .andInclude("friend_id", "since", "last_interacted_on")
                        .and("friend.user_id").as("userId")
                        .and("friend.name").as("userName")
                        .and("friend.avatar_url").as("avatarUrl"),
                sort(Sort.by(Sort.Direction.DESC, "last_interacted_on"))
        );

        return aggregateWithMapper(aggregation, "friendships", document -> {
            // Safely extract numeric IDs as Number → longValue()
            Number userIdNum = document.get("userId", Number.class);
            Long friendUserId = userIdNum != null ? userIdNum.longValue() : null;

            // Safe conversion for timestamps
            Date sinceDate = document.get("since", Date.class);
            Instant sinceInstant = (sinceDate != null) ? sinceDate.toInstant() : null;

            Date lastInteractedDate = document.get("last_interacted_on", Date.class);
            Instant lastInteractedInstant = (lastInteractedDate != null) ? lastInteractedDate.toInstant() : null;

            return new FriendshipDto(
                    friendUserId,
                    document.getString("userName"),
                    document.getString("avatarUrl"),
                    sinceInstant,
                    lastInteractedInstant
            );
        });
    }
}
