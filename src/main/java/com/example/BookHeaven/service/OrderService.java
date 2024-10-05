package com.example.BookHeaven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BookHeaven.model.Order;
import com.example.BookHeaven.model.OrderItem;
import com.example.BookHeaven.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(String id, Order order) {
        order.setId(id); // Ensure the ID is set
        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    // New method to get all orders for a specific user
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(String id, String status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setOrderStatus(status);
            return orderRepository.save(order);
        }
        return null; // Return null if the order doesn't exist
    }

    public List<Order> splitAndCreateOrders(Order order) {
        List<OrderItem> physicalItems = new ArrayList<>();
        List<OrderItem> digitalItems = new ArrayList<>();

        // Split items into physical and digital lists
        for (OrderItem item : order.getOrderItems()) {
            if ("physical".equals(item.getBookType())) {
                physicalItems.add(item);
            } else if ("digital".equals(item.getBookType())) {
                digitalItems.add(item);
            }
        }

        List<Order> createdOrders = new ArrayList<>();

        // Create physical order if it has items
        if (!physicalItems.isEmpty()) {
            Order physicalOrder = new Order();
            physicalOrder.setUserId(order.getUserId());
            physicalOrder.setOrderDate(order.getOrderDate());
            physicalOrder.setDeliveryAddress(order.getDeliveryAddress());
            physicalOrder.setTotalPrice(calculateTotalPrice(physicalItems)); // Recalculate price
            physicalOrder.setPaymentMethod(order.getPaymentMethod());
            physicalOrder.setOrderStatus(order.getOrderStatus());
            physicalOrder.setIsDigitalOrder(false); // Indicate it's not a digital order
            physicalOrder.setOrderItems(physicalItems);
            createdOrders.add(orderRepository.save(physicalOrder)); // Save physical order
        }

        // Create digital order if it has items
        if (!digitalItems.isEmpty()) {
            Order digitalOrder = new Order();
            digitalOrder.setUserId(order.getUserId());
            digitalOrder.setOrderDate(order.getOrderDate());
            digitalOrder.setDeliveryAddress(order.getDeliveryAddress());
            digitalOrder.setTotalPrice(calculateTotalPrice(digitalItems)); // Recalculate price
            digitalOrder.setPaymentMethod(order.getPaymentMethod());
            digitalOrder.setOrderStatus(order.getOrderStatus());
            digitalOrder.setIsDigitalOrder(true); // Indicate it's a digital order
            digitalOrder.setOrderItems(digitalItems);
            createdOrders.add(orderRepository.save(digitalOrder)); // Save digital order
        }

        return createdOrders;
    }

    private double calculateTotalPrice(List<OrderItem> items) {
        double totalPrice = 0;
        for (OrderItem item : items) {
            if ("physical".equals(item.getBookType())) {
                totalPrice += item.getBook().getPhysicalPrice() * item.getQuantity();
            } else if ("digital".equals(item.getBookType())) {
                totalPrice += item.getBook().getDigitalPrice() * item.getQuantity();
            }
        }
        return totalPrice;
    }

}
