package com.example.urlgeneration.dtos;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String code,
        Instant timestamp
) {
}