package com.ravijar.dinevote.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value("${foursquare.api.url}")
    private String fourSquareApiUrl;

    @Value("${foursquare.api.key}")
    private String fourSquareApiKey;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String fourSquareApiUrl() {
        return fourSquareApiUrl;
    }

    @Bean
    public String fourSquareApiKey() {
        return fourSquareApiKey;
    }
}
