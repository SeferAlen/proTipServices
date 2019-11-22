package com.protip.proTipServices.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class for app configuration
 */
@Configuration
public class Config {
    private static final String CHAT_QUEUE = "proTipServicesQueueChat";
    private static final String NOTIFICATION_QUEUE = "proTipServicesQueueNotification";

    @Value("${rabbitmq.url}")
    private String HOST;

    @Value("${rabbitmq.user}")
    private String CLIENT_LOGIN;

    @Value("${rabbitmq.password}")
    private String CLIENT_PASSWORD;

    @Value("${rabbitmq.port}")
    private int PORT;

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

    @Bean
    public ConnectionFactory connectionFactory() {
        final CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setUsername(CLIENT_LOGIN);
        factory.setPassword(CLIENT_PASSWORD);
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setVirtualHost("/");

        return factory;
    }
}
