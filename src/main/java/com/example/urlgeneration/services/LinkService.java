package com.example.urlgeneration.services;
import com.example.urlgeneration.dtos.LinkGenerateRequest;
import com.example.urlgeneration.dtos.LinkGenerateResponse;
import com.example.urlgeneration.dtos.LinkValidatorResponse;
import com.example.urlgeneration.utils.TokenGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LinkService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.link.base-url}")
    private String base_url;

    @Value("${app.link.expiry}")
    private long expiry;

    public LinkService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public LinkGenerateResponse generateLink(LinkGenerateRequest req) throws JsonProcessingException {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", req.getName());
        userData.put("email", req.getEmail());
        userData.put("contact_no", req.getContactNo());

        String json = objectMapper.writeValueAsString(userData);
        String token = TokenGenerator.generateToken(16);
        String url = base_url + "?token=" + token;

        redisTemplate.opsForValue().set(token, json, expiry, TimeUnit.MINUTES);

        return new LinkGenerateResponse(token, Instant.now(), url);
    }

    public LinkValidatorResponse validateLink(String token){
        if (Boolean.TRUE.equals(redisTemplate.hasKey(token))) {
            return new LinkValidatorResponse("Successfully Validated", null);
        } else {
            return new LinkValidatorResponse("Token is invalid or expired", "ERR_001");
        }
    }
}
