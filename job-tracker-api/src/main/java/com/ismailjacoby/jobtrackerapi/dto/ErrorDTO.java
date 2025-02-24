package com.ismailjacoby.jobtrackerapi.dto;

import java.time.LocalDateTime;

public record ErrorDTO(
        String message,
        LocalDateTime requestMadeAt,
        String URI
) {
}
