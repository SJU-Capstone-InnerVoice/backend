package com.innervoice.talk.dto.request;

public record TalkCreateRequest(
        Long userId,
        String title,
        String content,
        int duration,
        String startAt
) {
}
