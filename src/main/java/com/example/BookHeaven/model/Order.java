package com.example.BookHeaven.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id; // Order ID
    private String userId; // User who placed the order
    private String orderDate; // Date of the order
    private String deliveryAddress; // Delivery address for the order
    private double totalPrice; // Total price for the order
    private String paymentMethod; // Payment method used
    private String orderStatus; // Status of the order (e.g., Pending, Completed)
    private boolean IsDigitalOrder; // Indicates if the order is digital
    private List<OrderItem> orderItems; // List of order items
}
