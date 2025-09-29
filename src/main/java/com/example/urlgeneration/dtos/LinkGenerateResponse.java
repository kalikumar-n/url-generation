package com.example.urlgeneration.dtos;

import java.time.Instant;

public record LinkGenerateResponse(Long id, String token, Instant expiresAt, String secureUrl) {

}

