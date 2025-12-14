package com.demo.order.coordinator.eventhandlers;

import com.demo.order.commands.UpdateOrderCommand;
import com.demo.shipment.events.OrderShippedEvent;
import jakarta.inject.Inject;
import libs.axiom.messaging.abstractions.Bus;
import libs.axiom.messaging.abstractions.EventHandler;

public class OrderShippedEventHandler implements EventHandler<OrderShippedEvent> {

    private final Bus bus;

    @Inject
    public OrderShippedEventHandler(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void handle(OrderShippedEvent event) {

        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand();

        updateOrderCommand.setOrderId(event.getOrderId());
        updateOrderCommand.setShipmentId(event.getShipmentId());

        bus.send(updateOrderCommand);
    }
}
