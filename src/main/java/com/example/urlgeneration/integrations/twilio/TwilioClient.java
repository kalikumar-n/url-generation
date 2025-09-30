package com.example.urlgeneration.integrations.twilio;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "twilioClient", url = "${twilio.base-url}", configuration = TwilioConfig.class)
public interface TwilioClient {

    @PostMapping(value = "Accounts/{accountSid}/Messages.json", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void sendMessage(
            @PathVariable("accountSid") String accountSid,
            @RequestParam("To") String to,
            @RequestParam("From") String from,
            @RequestParam("Body") String body
    );
}
