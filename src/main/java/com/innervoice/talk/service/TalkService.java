package com.innervoice.talk.service;

import com.innervoice.talk.domain.Talk;
import com.innervoice.talk.dto.request.TalkCreateRequest;
import com.innervoice.talk.dto.response.TalkResponse;
import com.innervoice.talk.repository.TalkRepository;
import com.innervoice.user.domain.User;
import com.innervoice.user.service.FindUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TalkService {

    private final FindUserService findUserService;
    private final TalkRepository talkRepository;


    public TalkResponse createTalk(TalkCreateRequest request){
        User user = findUserService.findUserById(request.userId());
        Talk talk = Talk.builder()
                .user(user)
                .title(request.title())
                .content(request.content())
                .duration(request.duration())
                .startAt(request.startAt())
                .build();
        Talk savedTalk = talkRepository.save(talk);
        return TalkResponse.from(savedTalk);
    }

    public List<TalkResponse> getUserTalks(Long userId) {
        User user = findUserService.findUserById(userId);
        List<TalkResponse> responses = talkRepository.findAllByUser(user)
                .stream()
                .map(TalkResponse::from)
                .toList();
        return responses;
    }
}
