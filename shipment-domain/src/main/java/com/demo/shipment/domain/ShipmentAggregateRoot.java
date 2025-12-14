package com.demo.shipment.domain;

import com.demo.shipment.events.OrderShippedEvent;
import com.demo.shipment.events.ShipmentStatus;
import libs.axiom.data.abstractions.models.AggregateRoot;

import java.util.UUID;

public class ShipmentAggregateRoot extends AggregateRoot {


    private String itemName;

    private double quantity;

    private UUID orderId;

    private ShipmentStatus shipmentStatus;

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

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void shipOrder(UUID id, UUID orderId, String itemName, double quantity) {

        this.setId(id);
        this.setOrderId(orderId);
        this.setItemName(itemName);
        this.setQuantity(quantity);
        this.setShipmentStatus(ShipmentStatus.SHIPPED);

        OrderShippedEvent event = new OrderShippedEvent();

        event.setId(this.getId());
        event.setOrderId(orderId);
        event.setShipmentId(this.getId());
        event.setItemName(this.getItemName());
        event.setQuantity(this.getQuantity());
        event.setShipmentStatus(shipmentStatus);

        this.addEvent(event);
    }
}
