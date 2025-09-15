package com.example.urlgeneration.dtos;

import java.time.Instant;

public record LinkGenerateResponse(String token, Instant expiresAt) {}

