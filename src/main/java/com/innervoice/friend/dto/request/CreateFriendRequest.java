package com.innervoice.friend.dto.request;

public record CreateFriendRequest(
        Long userId,
        Long friendId
) {
}
