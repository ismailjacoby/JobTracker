package com.ismailjacoby.jobtrackerapi.dto;

import com.ismailjacoby.jobtrackerapi.enums.UserRole;
import lombok.Builder;

public record AuthDTO (
        String username,
        String token,
        UserRole role
) {
}
