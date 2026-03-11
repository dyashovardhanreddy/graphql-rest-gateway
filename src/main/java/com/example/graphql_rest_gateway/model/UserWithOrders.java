package com.example.graphql_rest_gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithOrders {
    private Long id;
    private String name;
    private String email;
    private List<Order> orders;
}
