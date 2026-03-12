package com.example.graphql_rest_gateway.controller;

import com.example.graphql_rest_gateway.model.Order;
import com.example.graphql_rest_gateway.model.User;
import com.example.graphql_rest_gateway.model.UserWithOrders;
import com.example.graphql_rest_gateway.service.OrderRestService;
import com.example.graphql_rest_gateway.service.UserRestService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class UserGraphQLController {

    private final UserRestService userRestService;
    private final OrderRestService orderRestService;

    public UserGraphQLController(UserRestService userRestService, OrderRestService orderRestService){
        this.userRestService = userRestService;
        this.orderRestService = orderRestService;
    }

    @QueryMapping
    public Mono<User> user(@Argument Long id){
        return userRestService.getUserById(id);
    }

    @QueryMapping
    public Mono<List<Order>> userOrders(@Argument Long userId){
        return orderRestService.getOrdersByUserId(userId);
    }

    @QueryMapping
    public Mono<UserWithOrders> userWithOrders(@Argument Long id){
        return Mono.zip(
                userRestService.getUserById(id),
                orderRestService.getOrdersByUserId(id)
        ).map(tuple -> new UserWithOrders(
                tuple.getT1().getId(),
                tuple.getT1().getName(),
                tuple.getT1().getEmail(),
                tuple.getT2()
        ));
    }
}
