package com.tms.easyrento.chat.repo;

import com.tms.easyrento.chat.dto.ConversationUserInfoFlat;
import com.tms.easyrento.chat.model.User;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

    @Aggregation(pipeline = {
            "{ $match: { user_id: { $in: [?0, ?1] } } }",
            "{ $facet: { " +
                    "sender: [ { $match: { user_id: ?0 } }, " +
                    "           { $project: { _id: 0, senderUserId: '$user_id', senderUserName: '$name' } } ]," +
                    "receiver: [ { $match: { user_id: ?1 } }, " +
                    "           { $project: { _id: 0, receiverUserId: '$user_id', receiverUserName: '$name' } } ]" +
                    "} }",
            "{ $project: { " +
                    "senderUserId: { $arrayElemAt: ['$sender.senderUserId', 0] }, " +
                    "senderUserName: { $arrayElemAt: ['$sender.senderUserName', 0] }, " +
                    "receiverUserId: { $arrayElemAt: ['$receiver.receiverUserId', 0] }, " +
                    "receiverUserName: { $arrayElemAt: ['$receiver.receiverUserName', 0] } " +
                    "} }"
    })
    ConversationUserInfoFlat findConversingPartiesInfoBy(Long senderId, Long receiverId);
}
