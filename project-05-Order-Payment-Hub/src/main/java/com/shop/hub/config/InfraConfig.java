package com.shop.hub.config;


import com.shop.external.SmsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfig {
    @Bean(initMethod = "connect", destroyMethod = "disconnect")
    public SmsClient smsClient(@Value("${sms.host:localhost}") String host) {
        return new SmsClient(host);
    }
}
