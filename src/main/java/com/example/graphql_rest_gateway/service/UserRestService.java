package com.example.graphql_rest_gateway.service;

import com.example.graphql_rest_gateway.model.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserRestService {


    private final WebClient webClient;

    public UserRestService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<User> getUserById(Long id){
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                    Mono.error(new RuntimeException("User not found for id: " + id)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new RuntimeException("User service is currently unavailable")))
                .bodyToMono(User.class);
    }
}
