package com.demo.order.coordinator.eventhandlers;

import com.demo.order.events.OrderPlacedEvent;
import com.demo.shipment.commands.ShipOrderCommand;
import jakarta.inject.Inject;
import libs.axiom.messaging.abstractions.Bus;
import libs.axiom.messaging.abstractions.EventHandler;

public class OrderPlacedEventHandler implements EventHandler<OrderPlacedEvent> {

    private final Bus bus;

    @Inject
    public OrderPlacedEventHandler(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void handle(OrderPlacedEvent event) {

        ShipOrderCommand shipOrderCommand = new ShipOrderCommand();

        shipOrderCommand.setOrderId(event.getOrderId());

        shipOrderCommand.setItemName(event.getItemName());

        shipOrderCommand.setQuantity(event.getQuantity());

        shipOrderCommand.setShipmentId(event.getShipmentId());

        bus.send(shipOrderCommand);
    }
}
