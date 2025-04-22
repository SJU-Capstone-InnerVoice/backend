package com.innervoice.user.controller;

import com.innervoice.user.dto.request.UserCreateRequest;
import com.innervoice.user.dto.response.UserCreateResponse;
import com.innervoice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboard")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserCreateResponse onboard(@RequestBody UserCreateRequest request) {
        return userService.onboard(request);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
