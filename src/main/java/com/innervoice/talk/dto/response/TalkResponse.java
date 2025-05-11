package com.innervoice.talk.dto.response;

import com.innervoice.talk.domain.Talk;

public record TalkResponse(
        Long id,
        String title,
        String content,
        int duration,
        String startAt
) {
    public static TalkResponse from(Talk talk) {
        return new TalkResponse(
                talk.getId(),
                talk.getTitle(),
                talk.getContent(),
                talk.getDuration(),
                talk.getStartAt()
        );
    }
}