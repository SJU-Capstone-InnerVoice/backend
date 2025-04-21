package com.innervoice.user.dto.response;

public record UserCreateResponse(
        Long id
) {

    public static UserCreateResponse of(Long id) {
        return new UserCreateResponse(id);
    }
}
