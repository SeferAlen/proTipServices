package com.protip.proTipServices.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class for app configuration
 */
@Configuration
public class Config {

    /**
     * Bean for PasswordEncoder
     *
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Queue chatQueue() {
        return new Queue("proTipServicesQueueChat", false);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("proTipServicesQueueNotification", false);
    }
}
