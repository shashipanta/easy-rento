package com.tms.easyrento.chat.service.impl;

import com.tms.easyrento.chat.dto.FriendshipDto;
import com.tms.easyrento.chat.model.Friendship;
import com.tms.easyrento.chat.repo.FriendshipRepo;
import com.tms.easyrento.chat.service.FriendshipService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-10-22 11:38
 */

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepo friendshipRepo;
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public void addFriendship(Long userId, Long friendId) {
        Instant now = Instant.now();

        Friendship aToB = new Friendship();
        aToB.setUserId(userId);
        aToB.setFriendId(friendId);
        aToB.setSince(now);
        aToB.setLastInteractedOn(now);

        Friendship bToA = new Friendship();
        bToA.setUserId(friendId);
        bToA.setFriendId(userId);
        bToA.setSince(now);
        bToA.setLastInteractedOn(now);

        mongoTemplate.insertAll(List.of(aToB, bToA));

    }

    @Override
    public List<FriendshipDto> getFriends(Long userId) {
        return friendshipRepo.getAllFriends(userId);
    }
}
