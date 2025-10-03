package com.example.urlgeneration.integrations.twilio;

import org.springframework.stereotype.Service;


@Service
public class TwilioService {

    private final TwilioClient client;
    private final TwilioProperties props;

    public TwilioService(TwilioClient client, TwilioProperties props) {
        this.client = client;
        this.props = props;
    }

    public void sendSms(String to, String url) {
        String body = "Here's your application link "+url;

        client.sendMessage(props.getAccountSid(), to, props.getFromNumber(), body);
    }
}
