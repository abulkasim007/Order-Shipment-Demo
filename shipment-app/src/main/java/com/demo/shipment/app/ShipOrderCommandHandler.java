package com.demo.shipment.app;

import com.demo.shipment.commands.ShipOrderCommand;
import com.demo.shipment.domain.ShipmentAggregateRoot;
import jakarta.inject.Inject;
import libs.axiom.data.abstractions.AggregateRootRepository;
import libs.axiom.messaging.abstractions.CommandHandler;

import java.util.UUID;

public class ShipOrderCommandHandler implements CommandHandler<ShipOrderCommand> {

    private final AggregateRootRepository<ShipmentAggregateRoot> aggregateRootRepository;

    @Inject
    public ShipOrderCommandHandler(AggregateRootRepository<ShipmentAggregateRoot> aggregateRootRepository) {
        this.aggregateRootRepository = aggregateRootRepository;
    }

    @Override
    public void handle(ShipOrderCommand command) {

        ShipmentAggregateRoot orderAggregateRoot = new ShipmentAggregateRoot();

        orderAggregateRoot.shipOrder(UUID.randomUUID(), command.getOrderId(), command.getItemName(), command.getQuantity());

        this.aggregateRootRepository.save(orderAggregateRoot);
    }
}
