package com.demo.order.commands;

import libs.axiom.messaging.abstractions.Command;

import java.util.UUID;

public class PlaceOrderCommand extends Command {

    private UUID orderId;

    private String itemName;

    private double quantity;

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

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
