package com.example.urlgeneration.controllers;

import com.example.urlgeneration.dtos.LinkGenerateRequest;
import com.example.urlgeneration.dtos.LinkGenerateResponse;
import com.example.urlgeneration.utils.TokenGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class LinkController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("links")
    public LinkGenerateResponse generateLink(@RequestBody LinkGenerateRequest req){
        Map<String, Object> user_data = new HashMap<>();
        user_data.put("name", req.getName());
        user_data.put("email", req.getEmail());
        user_data.put("contact_no", req.getContactNo());

        String token = TokenGenerator.generateToken(16);

        return new LinkGenerateResponse(token, Instant.now());
    }
}
