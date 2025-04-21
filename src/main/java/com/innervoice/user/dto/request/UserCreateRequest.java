package com.innervoice.user.dto.request;

import com.innervoice.user.domain.Role;

public record UserCreateRequest(
        String name,
        String password,
        Role role
) {
}
