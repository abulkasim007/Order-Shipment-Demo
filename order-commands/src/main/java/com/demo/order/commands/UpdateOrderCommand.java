package com.demo.order.commands;

import libs.axiom.messaging.abstractions.Command;

import java.util.UUID;

public class UpdateOrderCommand extends Command {

    private UUID orderId;

    private UUID shipmentId;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(UUID shipmentId) {
        this.shipmentId = shipmentId;
    }
}
