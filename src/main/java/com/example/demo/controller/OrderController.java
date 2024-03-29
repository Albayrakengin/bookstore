package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderRequest;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.exceptions.InsufficientOrderTotalException;
import com.example.demo.exceptions.InsufficientStockException;
import com.example.demo.exceptions.OrderNotFoundException;
import com.example.demo.service.OrderService;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;


    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void placeOrder(@RequestBody OrderRequest orderRequest) throws InsufficientStockException, BookNotFoundException, InsufficientOrderTotalException {
        orderService.placeNewOrder(orderRequest);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrdersForUser(@PathVariable Long userId) {
        List<Order> userOrders = orderService.getOrdersForUser(userId);
        return ResponseEntity.ok(userOrders);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetailsById(@PathVariable Long orderId) throws OrderNotFoundException {
        Map<String, Object> orderDetails = orderService.getOrderDetailsById(orderId);
        return ResponseEntity.ok(orderDetails);
    }
}