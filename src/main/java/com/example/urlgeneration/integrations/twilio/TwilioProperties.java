package com.example.urlgeneration.integrations.twilio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.*;

@Getter
@Setter
@ConfigurationProperties(prefix = "twilio")
public class TwilioProperties {
    private String accountSid;
    private String authToken;
    private String fromNumber;
    private String baseUrl;
}