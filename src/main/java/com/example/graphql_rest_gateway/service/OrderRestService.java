package com.example.graphql_rest_gateway.service;

import com.example.graphql_rest_gateway.model.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderRestService {

    private final WebClient webClient;

    public OrderRestService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<List<Order>> getOrdersByUserId(Long userId){
        return webClient.get()
                .uri("/orders/user/{userId}",userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("Orders not found for userId: " + userId)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new RuntimeException("Order service is currently unavailable")))
                .bodyToFlux(Order.class)
                .collectList();
    }
}
