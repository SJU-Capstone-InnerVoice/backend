package com.innervoice.user.controller;

import com.innervoice.user.dto.request.UserCreateRequest;
import com.innervoice.user.dto.request.UserLoginRequest;
import com.innervoice.user.dto.response.UserCreateResponse;
import com.innervoice.user.dto.response.UserLoginResponse;
import com.innervoice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserCreateResponse onboard(@RequestBody UserCreateRequest request) {
        return userService.onboard(request);
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest request){
        return userService.login(request);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
