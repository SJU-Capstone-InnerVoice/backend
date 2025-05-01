package com.innervoice.friend.dto.response;

public record FriendSearchResponse(
        Long id,
        String name
) {

    public static FriendSearchResponse of(Long id, String name) {
        return new FriendSearchResponse(id, name);
    }
}
