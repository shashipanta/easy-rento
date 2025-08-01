package com.tms.easyrento.chat.repo;

import com.tms.easyrento.chat.model.ChatMessage;
import com.tms.easyrento.chat.projections.ChatResponseProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepo extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByReceiverId(String receiverId);

    List<ChatMessage> findByGroupId(String groupId);

    @Query("{ 'groupId': ?0 }")
    List<ChatResponseProjection> findByGroupIdOrderByTimestampDesc(Long groupId, Pageable pageable);

    @Query("{ $and: [ { $or: [ { senderId: '?0' }, { receiverId: '?0' } ] }, { groupId: null } ] }")
    List<ChatMessage> findPrivateMessagesByUserId(Long userId, Pageable pageable);

    @Query(value = "{ $and: [{$or: [ { senderId:  '?0'}, { receiverId: '?0'}]}, { groupId:  null}]}")
    List<ChatResponseProjection> findPrivateMessageBy(Long userId, Pageable pageable);

}
