package com.demo.order.app.command.handlers;

import com.demo.order.commands.UpdateOrderCommand;
import com.demo.order.domain.OrderAggregateRoot;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import libs.axiom.data.abstractions.AggregateRootRepository;
import libs.axiom.messaging.abstractions.CommandHandler;

import java.util.UUID;

public class UpdateOrderCommandHandler implements CommandHandler<UpdateOrderCommand> {

    private final AggregateRootRepository<OrderAggregateRoot> aggregateRootRepository;

    @Inject
    public UpdateOrderCommandHandler( AggregateRootRepository<OrderAggregateRoot> aggregateRootRepository) {
        this.aggregateRootRepository = aggregateRootRepository;
    }

    @Override
    @Transactional
    public void handle(UpdateOrderCommand command) {

        OrderAggregateRoot orderAggregateRoot = aggregateRootRepository.get(command.getOrderId());

        orderAggregateRoot.update(command.getShipmentId());

        this.aggregateRootRepository.update(orderAggregateRoot);
    }
}
