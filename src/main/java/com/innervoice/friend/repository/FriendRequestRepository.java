package com.innervoice.friend.repository;

import com.innervoice.friend.domain.FriendRequest;
import com.innervoice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findAllByFriend(User friend);
}
