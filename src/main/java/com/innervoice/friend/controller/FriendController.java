package com.innervoice.friend.controller;

import com.innervoice.friend.dto.request.CreateFriendRequest;
import com.innervoice.friend.dto.response.FriendRequestResponse;
import com.innervoice.friend.dto.response.FriendResponse;
import com.innervoice.friend.dto.response.FriendSearchResponse;
import com.innervoice.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/requests")
    public ResponseEntity<Void> sendRequest(@RequestBody CreateFriendRequest request){
        friendService.createFriendRequest(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/requests")
    public List<FriendRequestResponse> getRequests(@RequestParam("userId") Long userId) {
        return friendService.listRequests(userId);
    }

    @PostMapping("/requests/{id}/accept")
    public ResponseEntity<Void> accept(@RequestParam("userId") Long userId, @PathVariable("id") Long requestId) {
        friendService.acceptRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<FriendResponse> getFriends(@RequestParam("userId") Long userId) {
        List<FriendResponse> response = friendService.listFriends(userId);
        return response;
    }

    @GetMapping("/search")
    public ResponseEntity<FriendSearchResponse> searchFriendByName(@RequestParam("name") String name) {
        FriendSearchResponse response = friendService.searchFriendByName(name);
        return ResponseEntity.ok(response);
    }
}
