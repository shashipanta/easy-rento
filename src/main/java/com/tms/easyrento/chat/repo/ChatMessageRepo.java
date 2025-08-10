package com.tms.easyrento.chat.repo;

import com.tms.easyrento.chat.dto.ChatResponseDto;
import com.tms.easyrento.chat.model.ChatMessage;
import com.tms.easyrento.chat.projections.ChatResponseProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepo extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByReceiverId(String receiverId);

    List<ChatMessage> findByGroupId(String groupId);

    @Query("{ 'groupId': ?0 }")
    List<ChatResponseDto> findByGroupIdOrderByTimestampDesc(Long groupId, Pageable pageable);

    @Query("{ $and: [ { $or: [ { senderId: '?0' }, { receiverId: '?0' } ] }, { groupId: null } ] }")
    List<ChatMessage> findPrivateMessagesByUserId(Long userId, Pageable pageable);

    @Aggregation(pipeline = {
            """
                    {
                        $match: {
                            $and: [
                                { message_type: 'PRIVATE' },
                                { $or: [ { sender_id: ?0 }, { receiver_id: ?0 } ] }
                            ],
                        }
                    }
                    """,
            """
                    { $lookup: { from: 'users', localField: 'sender_id', foreignField: 'id', as: 'sender' } }
                    """,
            """
                    { $unwind: '$sender' }
                    """,
            """
                    { $lookup: { from: 'users', localField: 'receiver_id', foreignField: 'id', as: 'receiver' } }
                    """,
            """
                    { $unwind: '$receiver' }
                    """,
            """
                    {
                        $project: {
                            id: '$_id',
                            content: 1,
                            time_stamp: 1,
                            type: { $ifNull: [ "$message_type", "PRIVATE" ] },
                            senderId: '$sender.id',
                            senderName: '$sender.name',
                            receiverId: '$receiver.id',
                            receiverName: '$receiver.name',
                            edited: 1
                        }
                    }
                    """
    })
    List<ChatResponseProjection> findPrivateMessageBy(Long userId);

    @Aggregation(pipeline = {
            """
            {
                $match: {
                    $and: [
                        { message_type: 'PRIVATE' },
                        { $or: [ { sender_id: ?0 }, { receiver_id: ?0 } ] }
                    ]
                }
            }
            """,
            """
            { $lookup: { from: 'users', localField: 'sender_id', foreignField: 'id', as: 'sender' } }
            """,
            """
            { $unwind: '$sender' }
            """,
            """
            { $lookup: { from: 'users', localField: 'receiver_id', foreignField: 'id', as: 'receiver' } }
            """,
            """
            { $unwind: '$receiver' }
            """,
            """
            {
                $project: {
                    id: '$_id',
                    content: 1,
                    timestamp: '$time_stamp',
                    type: { $ifNull: [ "$message_type", "PRIVATE" ] },
                    senderId: '$sender.id',
                    senderName: '$sender.name',
                    receiverId: '$receiver.id',
                    receiverName: '$receiver.name',
                    edited: 1
                }
            }
            """
    })
    List<ChatResponseDto> findPrivateMessageBys(Long userId);

}
