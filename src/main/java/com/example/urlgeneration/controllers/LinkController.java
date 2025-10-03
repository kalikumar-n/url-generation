package com.example.urlgeneration.controllers;

import com.example.urlgeneration.dtos.LinkGenerateRequest;
import com.example.urlgeneration.dtos.LinkGenerateResponse;
import com.example.urlgeneration.dtos.LinkValidatorResponse;
import com.example.urlgeneration.projections.UrlTokenProjection;
import com.example.urlgeneration.services.LinkService;
import com.example.urlgeneration.services.Notifications.NotificationService;
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
    private final List<NotificationService> notificationServices;

    public LinkController(LinkService linkService, List<NotificationService> notificationServices) {
        this.linkService = linkService;
        this.notificationServices = notificationServices;
    }

    @PostMapping("/links")
    public ResponseEntity<LinkGenerateResponse> generateLink(@RequestBody LinkGenerateRequest req) throws JsonProcessingException {
        LinkGenerateResponse response = linkService.generateLink(req);
        // Notify the users via SMS
        notificationServices.forEach(service -> service.notifyUser(req.getContactNo(), response.secureUrl()));
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

    @PutMapping("links/{id}/deactivate")
    public ResponseEntity<String>deactivateToken(@PathVariable Long id){
        linkService.deactivateToken(id);
        return ResponseEntity.accepted().body("URL deactivated successfully");
    }
}
