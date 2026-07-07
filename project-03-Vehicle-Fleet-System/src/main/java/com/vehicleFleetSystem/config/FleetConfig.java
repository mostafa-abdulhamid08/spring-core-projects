package com.vehicleFleetSystem.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.vehicleFleetSystem")
@Import(FleetBeanRegistrar.class)
public class FleetConfig {

}
