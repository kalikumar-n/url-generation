package com.example.urlgeneration.services;
import com.example.urlgeneration.dtos.LinkGenerateRequest;
import com.example.urlgeneration.dtos.LinkGenerateResponse;
import com.example.urlgeneration.dtos.LinkValidatorResponse;
import com.example.urlgeneration.model.UrlToken;
import com.example.urlgeneration.model.User;
import com.example.urlgeneration.projections.UrlTokenProjection;
import com.example.urlgeneration.repositories.UrlTokenRepository;
import com.example.urlgeneration.repositories.UserRepository;
import com.example.urlgeneration.utils.TokenGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LinkService {

    private final UrlTokenRepository urlTokenRepository;
    private final UserRepository userRepository;

    @Value("${app.link.base-url}")
    private String baseUrl;

    @Value("${app.link.expiry}")
    private long expiryMinutes;

    public LinkService(UrlTokenRepository urlTokenRepository, UserRepository userRepository) {
        this.urlTokenRepository = urlTokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LinkGenerateResponse generateLink(LinkGenerateRequest req) throws JsonProcessingException {
        String token = TokenGenerator.generateToken(16);
        String url = baseUrl + "?token=" + token;

        User user = createUser(req);
        UrlToken urlToken = createUrlToken(token, user);

        user.addToken(urlToken);
        userRepository.save(user);

        return new LinkGenerateResponse(urlToken.getId(), token, urlToken.getExpiredAt(), url);
    }

    @Transactional(readOnly = true)
    public LinkValidatorResponse validateLink(String token){
        UrlToken tokenRecord = urlTokenRepository.findByToken(token);

        if (tokenRecord != null && tokenRecord.getExpiredAt().isAfter(Instant.now()) && tokenRecord.getActive()) {
            return new LinkValidatorResponse("Successfully Validated", null);
        } else {
            return new LinkValidatorResponse("Token is invalid or expired", "ERR_001");
        }
    }

    public List<UrlTokenProjection> listTokens(Boolean status){
        return urlTokenRepository.findByStatus(status);
    }

    @Transactional
    public void deactivateToken(Long id){
        urlTokenRepository.updateActive(id, false);
    }

    private User createUser(LinkGenerateRequest req){
        User user = User.builder()
                        .name(req.getName())
                        .email(req.getEmail())
                        .phoneNo(req.getContactNo())
                        .build();
        return userRepository.save(user);
    }

    private UrlToken createUrlToken(String token, User user){
        UrlToken urlToken = UrlToken.builder()
                                    .token(token)
                                    .expiredAt(Instant.now().plus(expiryMinutes, ChronoUnit.MINUTES))
                                    .user(user)
                                    .active(true)
                                    .build();

        return urlTokenRepository.save(urlToken);
    }
}
