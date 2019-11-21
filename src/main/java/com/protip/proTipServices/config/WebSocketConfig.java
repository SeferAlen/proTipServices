package com.protip.proTipServices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Class for Web socket configuration
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final String APP_DESTINATION_PREFIX = "/app";
    private static final String DESTINATION_PREFIX = "/topic";
    private static final String STOMP_ENDPOINT = "/proTipServicesWebSocket";
    private static final String ORIGINS_ALL = "*";
    private static final String HOST = "amqp://sscieecx:Kg975wk8HHeJMxSyolwHngwRlqBthbEP@bullfrog.rmq.cloudamqp.com/sscieecx";
    private static final String CLIENT_LOGIN = "guest";
    private static final String CLIENT_PASSWORD = "guest";
    private static final int PORT = 61613;

    /**
     * Method for message broker configuration
     *
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(APP_DESTINATION_PREFIX);
        registry.enableStompBrokerRelay(DESTINATION_PREFIX)
                .setRelayHost(HOST)
                .setRelayPort(PORT)
                .setClientLogin(CLIENT_LOGIN)
                .setClientPasscode(CLIENT_PASSWORD);
    }

    /**
     * Method for registering stomp endpoint
     *
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(STOMP_ENDPOINT)
                .setAllowedOrigins(ORIGINS_ALL)
                .withSockJS();
    }
}
