package com.innervoice.friend.repository;

import com.innervoice.friend.domain.Friendship;
import com.innervoice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findAllByUser(User user);
}
