package com.example.graphql_rest_gateway.service;

import com.example.graphql_rest_gateway.model.User;
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
                .uri("/user/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }
}
