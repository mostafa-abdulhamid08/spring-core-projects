package com.shop.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.time.Clock;
import java.time.ZoneId;
import java.util.Locale;

@Configuration
public class InfraConfig {
    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of("Africa/Cairo"));
    }

    @Bean
    public NumberFormat numberFormat() {
        return NumberFormat.getIntegerInstance(Locale.US);
    }
}
