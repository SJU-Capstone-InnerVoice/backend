package com.innervoice.friend.dto.response;

import com.innervoice.friend.domain.Friendship;

public record FriendResponse(
        Long friendId,
        String friendName
) {

    public static FriendResponse from(Friendship friendship) {
        return new FriendResponse(
                friendship.getFriend().getId(),
                friendship.getFriend().getName()
        );
    }
}
