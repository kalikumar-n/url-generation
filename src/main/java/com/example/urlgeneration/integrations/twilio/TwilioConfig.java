package com.example.urlgeneration.integrations.twilio;

import com.example.urlgeneration.integrations.twilio.errors.TwilioErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;


@Configuration
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
            requestTemplate.header("Accept", "application/json");
        };
    }

    @Bean
    public ErrorDecoder twilioErrorDecoder() {
        return new TwilioErrorDecoder();
    }

}
