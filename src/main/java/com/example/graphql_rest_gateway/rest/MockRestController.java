package com.example.graphql_rest_gateway.rest;

import com.example.graphql_rest_gateway.model.Order;
import com.example.graphql_rest_gateway.model.User;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MockRestController {

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id){
        return new User(id, "John Doe", "john@example.com");
    }

    @GetMapping("/orders/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable long userId){
        return List.of(
                new Order(101L, userId, "Laptop", 999.99),
                new Order(102L, userId, "Mouse", 49.99),
                new Order(103L, userId, "Keyboard", 79.99)
        );
    }
}
