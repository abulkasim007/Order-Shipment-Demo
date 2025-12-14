package com.demo.order.events;

import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.Entity;
import libs.axiom.messaging.abstractions.Event;

import java.util.UUID;

@Entity
@QueryEntity
public class OrderCompletedEvent extends Event {

    private UUID orderId;

    private String itemName;

    private double quantity;

    private UUID shipmentId;

    private OrderStatus orderStatus;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public UUID getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(UUID shipmentId) {
        this.shipmentId = shipmentId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

