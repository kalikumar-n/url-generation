package com.example.urlgeneration.controllers;

import com.example.urlgeneration.dtos.LinkGenerateRequest;
import com.example.urlgeneration.dtos.LinkGenerateResponse;
import com.example.urlgeneration.dtos.LinkValidatorResponse;
import com.example.urlgeneration.utils.TokenGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1")
public class LinkController {
    private final StringRedisTemplate redisTemplate;

    public LinkController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/links")
    public ResponseEntity<LinkGenerateResponse> generateLink(@RequestBody LinkGenerateRequest req) throws JsonProcessingException {
        Map<String, Object> user_data = new HashMap<>();
        user_data.put("name", req.getName());
        user_data.put("email", req.getEmail());
        user_data.put("contact_no", req.getContactNo());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user_data);
        String token = TokenGenerator.generateToken(16);
        redisTemplate.opsForValue().set(token, json, 10, TimeUnit.MINUTES);

        LinkGenerateResponse response = new LinkGenerateResponse(token, Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping("/links/{token}")
    public ResponseEntity<LinkValidatorResponse> validateLink(@PathVariable String token) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(token))) {
            String json = (String) redisTemplate.opsForValue().get(token);
            LinkValidatorResponse response = new LinkValidatorResponse("Successfully Validated", null);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            LinkValidatorResponse response = new LinkValidatorResponse("Token is invalid or expired", "ERR_001");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
