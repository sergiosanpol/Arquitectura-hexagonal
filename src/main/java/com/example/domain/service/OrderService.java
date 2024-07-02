package com.example.domain.service;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        orderRepository.persist(order);
        return order;
    }

    @Transactional
    public void addItemToOrder(Long orderId, OrderItem item){
        Order order = orderRepository.findById(orderId);

        if (order != null) {
            order.addItem(item);
            orderRepository.persist(order);
        }
    }

    @Transactional
    public void updateOrderStatus(Long orderId, String status){
        Order order = orderRepository.findById(orderId);

        if (order != null) {
            order.updateStatus(status);
            orderRepository.persist(order);
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.listAll();
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

}
