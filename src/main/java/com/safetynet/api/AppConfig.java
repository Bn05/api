package com.safetynet.api;


import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    protected HttpTraceRepository htttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}


