package com.protip.proTipServices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Class for Web socket configuration
 */
@Configuration
// @EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    private static final String APP_DESTINATION_PREFIX = "/app";
//    private static final String DESTINATION_PREFIX = "/topic";
//    private static final String STOMP_ENDPOINT = "/proTipServicesWebSocket";
//    private static final String ORIGINS_ALL = "*";
//
//    @Value("${rabbitmq.url}")
//    private String HOST;
//
//    @Value("${rabbitmq.user}")
//    private String CLIENT_LOGIN;
//
//    @Value("${rabbitmq.password}")
//    private String CLIENT_PASSWORD;
//
//    @Value("${rabbitmq.stomp.port}")
//    private int PORT;
//
//    /**
//     * Method for message broker configuration
//     *
//     */
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes(APP_DESTINATION_PREFIX);
//        registry.enableStompBrokerRelay(DESTINATION_PREFIX)
//                .setRelayHost(HOST)
//                .setRelayPort(PORT)
//                .setClientLogin(CLIENT_LOGIN)
//                .setClientPasscode(CLIENT_PASSWORD);
//    }
//
//    /**
//     * Method for registering stomp endpoint
//     *
//     */
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint(STOMP_ENDPOINT)
//                .setAllowedOrigins(ORIGINS_ALL)
//                .withSockJS();
//    }
}
