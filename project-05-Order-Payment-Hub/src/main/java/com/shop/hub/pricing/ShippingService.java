package com.shop.hub.pricing;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ShippingService {

    private final int defaultFee;
    private final int freeThreshold;
    private final String[] freeCities;
    private final Environment env;

    public ShippingService(@Value("${shipping.default-fee:50}") int defaultFee,
                           @Value("${shipping.free-threshold:5000}") int freeThreshold,
                           @Value("${shipping.free-cities:}") String[] freeCities,
                           Environment env) {
        this.defaultFee = defaultFee;
        this.freeThreshold = freeThreshold;
        this.freeCities = freeCities;
        this.env = env;
    }
    public int feeFor(String city, int subtotal) {
        if (subtotal >= freeThreshold) {
            return 0;
        }
        if (Arrays.stream(freeCities).anyMatch(free -> free.equalsIgnoreCase(city))) {
            return 0;
        }
        String key = "shipping.city-fee." + city.toLowerCase();
        return env.getProperty(key, Integer.class, defaultFee);
    }
}
