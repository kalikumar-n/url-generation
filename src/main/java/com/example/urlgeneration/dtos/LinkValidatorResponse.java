package com.example.urlgeneration.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LinkValidatorResponse(String message, @Nullable String errorCode) {
}