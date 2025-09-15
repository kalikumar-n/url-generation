package com.example.urlgeneration.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import java.util.Objects;

public class LinkGenerateRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String contactNo;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }
}
