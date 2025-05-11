package com.innervoice.talk.controller;

import com.innervoice.talk.dto.request.TalkCreateRequest;
import com.innervoice.talk.dto.response.TalkResponse;
import com.innervoice.talk.service.TalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/talks")
public class TalkController {

    private final TalkService talkService;

    @PostMapping
    public ResponseEntity<TalkResponse> createTalk(@RequestBody TalkCreateRequest request){
        TalkResponse response = talkService.createTalk(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TalkResponse>> getUserTalks(@RequestParam("userId") Long userId){
        List<TalkResponse> response = talkService.getUserTalks(userId);
        return ResponseEntity.ok(response);
    }
}
