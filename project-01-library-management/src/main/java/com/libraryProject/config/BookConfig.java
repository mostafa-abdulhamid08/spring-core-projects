package com.libraryProject.config;

import com.libraryProject.beans.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BookConfig {
    @Bean(name = "cleanCodeBook")
    @Primary
    public Book cleancode()
    {
        System.out.println("Bean created: Clean Code");
        return new Book("Clean Code", "Robert C. Martin", 45);
    }
    @Bean(name = "effectiveJavaBook")
    public Book effectiveJava() {
        System.out.println("Bean created: Effective Java");
        return new Book("Effective Java", "Joshua Bloch", 55);
    }
    @Bean(name = "springInActionBook")
    public Book springInAction() {
        System.out.println("Bean created: Spring in Action");
        return new Book("Spring in Action", "Craig Walls", 60);
    }

}
