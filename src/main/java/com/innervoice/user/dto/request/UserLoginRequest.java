package com.innervoice.user.dto.request;

public record UserLoginRequest(
        String name,
        String password
) {
}
