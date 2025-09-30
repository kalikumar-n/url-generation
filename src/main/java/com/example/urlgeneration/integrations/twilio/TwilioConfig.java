package com.example.urlgeneration.integrations.twilio;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.util.Base64;

@Slf4j
public class TwilioConfig {
    private final TwilioProperties props;

    public TwilioConfig(TwilioProperties props) {
        this.props = props;
    }

    @Bean
    public RequestInterceptor twilioAuthInterceptor(){
        return requestTemplate -> {
            String creds = props.getAccountSid() + ":" + props.getAuthToken();
            String base64 = Base64.getEncoder().encodeToString(creds.getBytes());
            requestTemplate.header("Authorization", "Basic " + base64);
            log.info("[Feign->Twilio] Request...");
            requestTemplate.header("Accept", "application/json");
        };
    }
}
