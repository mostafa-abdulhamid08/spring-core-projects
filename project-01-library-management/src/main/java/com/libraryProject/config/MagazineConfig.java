package com.libraryProject.config;

import com.libraryProject.beans.Magazine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MagazineConfig {
    @Bean(name = "nationalGeographic")
    public Magazine nationalGeographic() {
        System.out.println("Bean created: National Geographic");
        return new Magazine("National Geographic", 245);
    }

    @Bean(name = "timesMagazine")
    public Magazine timesMagazine() {
        System.out.println("Bean created: Times Magazine");
        return new Magazine("Times Magazine", 89);
    }

}
