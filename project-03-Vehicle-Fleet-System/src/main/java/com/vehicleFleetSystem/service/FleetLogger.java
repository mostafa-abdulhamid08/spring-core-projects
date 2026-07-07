package com.vehicleFleetSystem.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class FleetLogger {
    @PostConstruct
    public void init()
    {
        System.out.println("FleetLogger: @PostConstruct → Fleet Logger Ready ✅");
    }
    public void log(String message)        { System.out.println("FleetLogger: " + message); }
    public void logSuccess(String message) { System.out.println("FleetLogger ✅: " + message); }
    public void logError(String message)   { System.out.println("FleetLogger ❌: " + message); }

    @PreDestroy
    public void destroy() {
        System.out.println("FleetLogger: @PreDestroy → Fleet Logger Shutting Down 🛑");
    }

}
