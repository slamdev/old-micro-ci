package com.github.slamdev.starter.integration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class CorsResolverAutoConfiguration {

    @Configuration
    @ConditionalOnWebApplication
    @Order(0)
    protected static class CorsResolverMvcConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**");
        }
    }
}
