package com.demo.order.query.event.handlers;

import com.demo.order.events.OrderCompletedEvent;
import com.demo.order.query.view.models.OrderViewModel;
import jakarta.inject.Inject;
import libs.axiom.data.abstractions.ReadRepository;
import libs.axiom.messaging.abstractions.EventHandler;

public class OrderCompletedEventHandler implements EventHandler<OrderCompletedEvent> {

    private final ReadRepository readRepository;

    @Inject
    public OrderCompletedEventHandler(ReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    @Override
    public void handle(OrderCompletedEvent event) {

        OrderViewModel orderViewModel = new OrderViewModel();

        orderViewModel.setId(event.getOrderId());
        orderViewModel.setOrderStatus(event.getOrderStatus());
        orderViewModel.setShipmentId(event.getShipmentId());
        orderViewModel.setItemName(event.getItemName());
        orderViewModel.setQuantity(event.getQuantity());

        this.readRepository.save(orderViewModel, OrderViewModel.class);
    }
}
