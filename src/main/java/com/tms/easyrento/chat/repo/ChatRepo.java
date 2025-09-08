package com.tms.easyrento.chat.repo;

import com.tms.easyrento.chat.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepo extends MongoRepository<Chat, String> {

    List<Chat> findByParticipantsContaining(Long userId);

    @Query("{ 'participants' :  { $all:  [?0, ?1] }, 'participants':  { $size:  2 } }")
    Optional<Chat> findPrivateChat(Long user1, Long user2);
}
