package com.innervoice.user.controller;

import com.innervoice.user.dto.request.UserCreateRequest;
import com.innervoice.user.dto.response.UserCreateResponse;
import com.innervoice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onboard")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserCreateResponse onboard(@RequestBody UserCreateRequest request) {
        return userService.onboard(request);
    }
}
