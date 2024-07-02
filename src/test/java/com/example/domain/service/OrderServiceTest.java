package com.example.domain.service;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class OrderServiceTest {

    @Inject
    OrderService orderService;

    @Test
    public void testCreateOrder() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        orderService.createOrder(order);
        assertNotNull(order.getId());
    }

    @Test
    public void testAddItemToOrder() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        orderService.createOrder(order);
        OrderItem item = new OrderItem("Product", 2, new BigDecimal("50.0"));
        orderService.addItemToOrder(order.getId(), item);
        assertEquals(1, orderService.findOrderById(order.getId()).getItems().size());
    }

    @Test
    public void testUpdateOrderStatus() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        orderService.createOrder(order);
        orderService.updateOrderStatus(order.getId(), "CONFIRMED");
        assertEquals("CONFIRMED", orderService.findOrderById(order.getId()).getStatus());
    }

}
