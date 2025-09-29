package com.example.urlgeneration.controllers;

import com.example.urlgeneration.dtos.LinkGenerateRequest;
import com.example.urlgeneration.dtos.LinkGenerateResponse;
import com.example.urlgeneration.dtos.LinkValidatorResponse;
import com.example.urlgeneration.projections.UrlTokenProjection;
import com.example.urlgeneration.services.LinkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/links")
    public ResponseEntity<LinkGenerateResponse> generateLink(@RequestBody LinkGenerateRequest req) throws JsonProcessingException {
        LinkGenerateResponse response = linkService.generateLink(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping("/links/{token}")
    public ResponseEntity<LinkValidatorResponse> validateLink(@PathVariable String token) {
        LinkValidatorResponse response = linkService.validateLink(token);

        HttpStatus status = StringUtils.hasText(response.errorCode())
                ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

    @RequestMapping("/links/tokens")
    public ResponseEntity<List<UrlTokenProjection>> listActiveToken(@RequestParam(required = false, defaultValue = "true") Boolean active){
        return ResponseEntity.ok(linkService.listTokens(active));
    }

    @PostMapping("links/deactivatetoken/{id}")
    public ResponseEntity<String>deactivateToken(@PathVariable Long id){
        linkService.deactivateToken(id);
        return ResponseEntity.accepted().body("Token deactivated successfully");
    }
}
