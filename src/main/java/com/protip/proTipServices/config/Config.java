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
        final URI rabbitMqUrl;
        try {
            rabbitMqUrl = new URI(System.getenv(HOST));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        final CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setUsername(rabbitMqUrl.getUserInfo().split(":")[0]);
        factory.setPassword(rabbitMqUrl.getUserInfo().split(":")[1]);
        factory.setHost(rabbitMqUrl.getHost());
        factory.setPort(rabbitMqUrl.getPort());
        factory.setVirtualHost(rabbitMqUrl.getPath().substring(1));

        return factory;
    }
}
