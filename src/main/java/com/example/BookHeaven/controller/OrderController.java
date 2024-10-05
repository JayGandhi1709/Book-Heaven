package com.example.BookHeaven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BookHeaven.Utils.JsonResponseUtils;
import com.example.BookHeaven.Utils.ResponseMessage;
import com.example.BookHeaven.model.Order;
import com.example.BookHeaven.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/orders")
    public ResponseEntity<Object> getOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(JsonResponseUtils
                            .toJson(new ResponseMessage<List<Order>>(true, "All Orders fetched successfully", orders)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    @GetMapping("/admin/orders/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable String id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "Order not found")));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // @PostMapping("/orders")
    // public ResponseEntity<Object> createOrder(@RequestBody Order order) {
    // try {
    // Order newOrder = orderService.createOrder(order);
    // return ResponseEntity.status(HttpStatus.CREATED)
    // .body(JsonResponseUtils
    // .toJson(new ResponseMessage<Order>(true, "Order created successfully",
    // newOrder)));
    // } catch (RuntimeException e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false,
    // e.getMessage())));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false,
    // e.getMessage())));
    // }
    // }

    @PostMapping("/orders")
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        try {
            // Split the order into two (physical and digital) and save them separately
            List<Order> createdOrders = orderService.splitAndCreateOrders(order);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(JsonResponseUtils.toJson(
                            new ResponseMessage<List<Order>>(true, "Orders created successfully", createdOrders)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    @PutMapping("/admin/orders/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable String id, @RequestBody Order order) {
        try {
            Order updatedOrder = orderService.updateOrder(id, order);
            return ResponseEntity.ok(JsonResponseUtils
                    .toJson(new ResponseMessage<Order>(true, "Order updated successfully", updatedOrder)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    @DeleteMapping("/admin/orders/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable String id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // New endpoint to get all orders for a specific user
    @GetMapping("/orders/{userId}")
    public ResponseEntity<Object> getOrdersByUserId(@PathVariable String userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(JsonResponseUtils
                    .toJson(new ResponseMessage<List<Order>>(true, "Orders fetched successfully", orders)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    @PutMapping("/admin/orders/{id}/status")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable String id, @RequestBody String status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return updatedOrder != null
                    ? ResponseEntity.ok(JsonResponseUtils.toJson(
                            new ResponseMessage<Order>(true, "Order status updated successfully", updatedOrder)))
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "Order not found")));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }
}
