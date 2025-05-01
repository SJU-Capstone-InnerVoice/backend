package com.innervoice.friend.service;

import com.innervoice.friend.domain.FriendRequest;
import com.innervoice.friend.domain.Friendship;
import com.innervoice.friend.dto.request.CreateFriendRequest;
import com.innervoice.friend.dto.response.FriendRequestResponse;
import com.innervoice.friend.dto.response.FriendResponse;
import com.innervoice.friend.dto.response.FriendSearchResponse;
import com.innervoice.friend.exception.FriendException;
import com.innervoice.friend.exception.FriendExceptionType;
import com.innervoice.friend.repository.FriendRequestRepository;
import com.innervoice.friend.repository.FriendshipRepository;
import com.innervoice.user.domain.User;
import com.innervoice.user.exception.UserException;
import com.innervoice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.innervoice.user.exception.UserExceptionType.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;

    public void createFriendRequest(CreateFriendRequest req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        User friend = userRepository.findById(req.friendId())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        FriendRequest friendRequest = FriendRequest.builder()
                .user(user)
                .friend(friend)
                .build();
        friendRequestRepository.save(friendRequest);
    }

    @Transactional(readOnly = true)
    public List<FriendRequestResponse> listRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        List<FriendRequest> friendRequests = friendRequestRepository.findAllByFriend(user);
        return friendRequests.stream()
                .map(FriendRequestResponse::from)
                .collect(Collectors.toList());
    }

    public void acceptRequest(Long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new FriendException(FriendExceptionType.FRIEND_REQUEST_NOT_FOUND));
        friendshipRepository.save(Friendship.builder()
                .user(friendRequest.getUser())
                .friend(friendRequest.getFriend())
                .build());
        friendshipRepository.save(Friendship.builder()
                .user(friendRequest.getFriend())
                .friend(friendRequest.getUser())
                .build());
        friendRequestRepository.delete(friendRequest);
    }

    @Transactional(readOnly = true)
    public List<FriendResponse> listFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        List<Friendship> friendships = friendshipRepository.findAllByUser(user);
        return friendships.stream()
                .map(FriendResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public FriendSearchResponse searchFriendByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new FriendException(FriendExceptionType.FRIEND_NOT_FOUND));
        return FriendSearchResponse.of(user.getId(), user.getName());
    }
}
