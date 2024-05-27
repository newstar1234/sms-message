package com.example.sms.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
public class SmsProvider {
    
    private final DefaultMessageService messageService;

    @Value("${sms.from-number}") String FROM;

    public SmsProvider(
        @Value("${sms.api-key}") String API_KEY,
        @Value("${sms.api-secret-key}") String API_SECRET_KEY,
        @Value("${sms.domain}") String DOMAIN
    ) {
        this.messageService = NurigoApp.INSTANCE.initialize(API_KEY, API_SECRET_KEY, DOMAIN);
    }

    public boolean sendSms(String to) {
        Message message = new Message();
        message.setFrom(FROM);
        message.setTo(to);
        message.setText("문자내용");

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));

        String statusCode = response.getStatusCode();
        boolean result = statusCode.equals("2000");

        return result;
    }

}
