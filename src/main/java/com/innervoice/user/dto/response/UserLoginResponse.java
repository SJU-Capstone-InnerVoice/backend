package com.innervoice.user.dto.response;

public record UserLoginResponse(
        Long id,
        String name,
        String role
) {

    public static UserLoginResponse of(Long id, String name, String role){
        return new UserLoginResponse(id, name, role);
    }
}
