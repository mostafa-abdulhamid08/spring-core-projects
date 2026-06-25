package com.libraryProject.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.libraryProject")
@Import({BookConfig.class, MagazineConfig.class})
public class LibraryConfig {
}
