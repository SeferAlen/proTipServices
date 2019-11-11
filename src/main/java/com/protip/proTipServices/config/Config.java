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
    private static final String CHAT_QUEUE = "proTipServicesQueueChat";
    private static final String NOTIFICATION_QUEUE = "proTipServicesQueueNotification";

    /**
     * Bean for PasswordEncoder
     *
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for chatQueue
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE, false);
    }

    /**
     * Bean for notificationQueue
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, false);
    }

    /**
     * Static method for getting chat queue name
     *
     * @return CHAT_QUEUE {@link String} the queue name
     */
    public static String getChatQueue() {
        return CHAT_QUEUE;
    }

    /**
     * Static method for getting notification queue name
     *
     * @return NOTIFICATION_QUEUE {@link String} the queue name
     */
    public static String getNotificationQueueQueue() {
        return NOTIFICATION_QUEUE;
    }
}
