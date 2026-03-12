package com.example.graphql_rest_gateway.service;

import com.example.graphql_rest_gateway.model.Order;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class OrderRestService {

    private final WebClient webClient;

    public OrderRestService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<List<Order>> getOrdersByUserId(Long userId){
        return webClient.get()
                .uri("/orders/user/{userId}",userId)
                .retrieve()
                .bodyToFlux(Order.class)
                .collectList();
    }
}
