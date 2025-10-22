package com.tms.easyrento.chat.service;

import com.tms.easyrento.chat.dto.FriendshipDto;

import java.util.List;

public interface FriendshipService {
    void addFriendship(Long userId, Long friendId);
    List<FriendshipDto> getFriends(Long userId);
}
