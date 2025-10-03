package com.example.urlgeneration.services.Notifications;

import com.example.urlgeneration.integrations.twilio.TwilioService;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService implements NotificationService{
    private final TwilioService twilioService;

    public SmsNotificationService(TwilioService twilioService){
        this.twilioService = twilioService;
    }

    public void notifyUser(String to, String body) {
        twilioService.sendSms(to, body);
    }
}
