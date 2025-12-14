package com.demo.order.app.command.handlers;

import com.demo.order.commands.PlaceOrderCommand;
import com.demo.order.domain.OrderAggregateRoot;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import libs.axiom.data.abstractions.AggregateRootRepository;
import libs.axiom.data.abstractions.StateRepository;
import libs.axiom.messaging.abstractions.CommandHandler;

import java.util.UUID;

public class PlaceOrderCommandHandler implements CommandHandler<PlaceOrderCommand> {

    private final StateRepository stateRepository;
    private final AggregateRootRepository<OrderAggregateRoot> aggregateRootRepository;

    @Inject
    public PlaceOrderCommandHandler(StateRepository stateRepository, AggregateRootRepository<OrderAggregateRoot> aggregateRootRepository) {
        this.stateRepository = stateRepository;
        this.aggregateRootRepository = aggregateRootRepository;
    }

    @Override
    @Transactional
    public void handle(PlaceOrderCommand command) {

        OrderAggregateRoot orderAggregateRoot = new OrderAggregateRoot();

        orderAggregateRoot.placeOrder(UUID.randomUUID(), command.getItemName(), command.getQuantity(), stateRepository);

        this.aggregateRootRepository.save(orderAggregateRoot);
    }
}
