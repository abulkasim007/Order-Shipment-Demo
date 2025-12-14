package com.demo.order.query.view.models;

import com.demo.order.events.OrderStatus;
import libs.axiom.data.abstractions.models.ViewModel;

import java.util.UUID;

public class OrderViewModel extends ViewModel {

    private String itemName;

    private double quantity;

    private OrderStatus orderStatus;

    private UUID shipmentId;

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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(UUID shipmentId) {
        this.shipmentId = shipmentId;
    }
}
