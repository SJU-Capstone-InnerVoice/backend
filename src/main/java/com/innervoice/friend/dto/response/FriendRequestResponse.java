package com.innervoice.friend.dto.response;

import com.innervoice.friend.domain.FriendRequest;

public record FriendRequestResponse(
        Long requestId,
        Long userId,
        String userName
) {

    public static FriendRequestResponse from(FriendRequest friendRequest) {
        return new FriendRequestResponse(
                friendRequest.getId(),
                friendRequest.getUser().getId(),
                friendRequest.getUser().getName()
        );
    }
}
