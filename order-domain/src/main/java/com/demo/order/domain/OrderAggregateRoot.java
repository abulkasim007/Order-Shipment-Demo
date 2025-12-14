package com.demo.order.domain;

import com.demo.order.events.OrderPlacedEvent;
import com.demo.order.events.OrderRuleViolatedEvent;
import com.demo.order.events.OrderCompletedEvent;
import com.demo.order.events.OrderStatus;
import com.querydsl.core.annotations.QueryEntity;
import com.querydsl.core.types.Predicate;
import jakarta.persistence.Entity;
import libs.axiom.data.abstractions.StateRepository;
import libs.axiom.data.abstractions.models.AggregateRoot;

import java.util.UUID;

@Entity
@QueryEntity
public class OrderAggregateRoot extends AggregateRoot {

    private static final QOrderAggregateRoot qOrderAggregateRoot = QOrderAggregateRoot.orderAggregateRoot;

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

    public void placeOrder(UUID id, String itemName, double quantity, StateRepository stateRepository) {

        this.setId(id);
        this.setItemName(itemName);
        this.setQuantity(quantity);
        this.setOrderStatus(OrderStatus.SHIPMENT_PENDING);

        if (id == null) {
            OrderRuleViolatedEvent orderRuleViolatedEvent = new OrderRuleViolatedEvent();
            this.addEvent(orderRuleViolatedEvent);
            return;
        }

        if (itemName == null) {
            OrderRuleViolatedEvent orderRuleViolatedEvent = new OrderRuleViolatedEvent();
            this.addEvent(orderRuleViolatedEvent);
            return;
        }

        if (quantity <= 0) {
            OrderRuleViolatedEvent orderRuleViolatedEvent = new OrderRuleViolatedEvent();
            this.addEvent(orderRuleViolatedEvent);
            return;
        }

        boolean idExists = stateRepository.exists(qOrderAggregateRoot.id.eq(id), OrderAggregateRoot.class);

        if (idExists) {
            OrderRuleViolatedEvent orderRuleViolatedEvent = new OrderRuleViolatedEvent();
            this.addEvent(orderRuleViolatedEvent);
            return;
        }

        Predicate filter = qOrderAggregateRoot.itemName.eq(itemName).and(qOrderAggregateRoot.quantity.goe(100.0));

        boolean fiveItemsSold = stateRepository.exists(filter, OrderAggregateRoot.class);

        if (fiveItemsSold) {
            OrderRuleViolatedEvent orderRuleViolatedEvent = new OrderRuleViolatedEvent();
            this.addEvent(orderRuleViolatedEvent);
            return;
        }

        OrderPlacedEvent event = new OrderPlacedEvent();


        event.setOrderId(this.getId());
        event.setItemName(this.getItemName());
        event.setQuantity(this.getQuantity());
        event.setOrderStatus(this.getOrderStatus());
        event.setShipmentId(this.getShipmentId());

        this.addEvent(event);
    }

    public void update(UUID shipmentId) {

        this.setShipmentId(shipmentId);
        this.setOrderStatus(OrderStatus.SHIPPED);

        OrderCompletedEvent event = new OrderCompletedEvent();

        event.setOrderId(this.getId());
        event.setItemName(this.getItemName());
        event.setQuantity(this.getQuantity());
        event.setOrderStatus(this.getOrderStatus());
        event.setShipmentId(this.getShipmentId());

        this.addEvent(event);
    }
}
