package com.demo.order.coordinator;

import com.demo.order.commands.UpdateOrderCommand;
import com.demo.order.coordinator.eventhandlers.OrderPlacedEventHandler;
import com.demo.order.coordinator.eventhandlers.OrderShippedEventHandler;
import com.demo.shipment.commands.ShipOrderCommand;
import com.google.inject.AbstractModule;
import libs.axiom.host.implementations.builder.Host;
import libs.axiom.host.implementations.builder.HostBuilder;
import libs.axiom.messaging.abstractions.MessageListeners;
import libs.axiom.messaging.abstractions.MessagePublishers;
import libs.axiom.messaging.abstractions.Retries;
import libs.axiom.messaging.rabbitmq.implementations.RabbitMQModule;
import libs.axiom.serialization.abstractions.SerializationFormat;

public class Main {

    static void main(String[] args) {

        Host host = new HostBuilder()
                // User provided services
                .addModule(new UserBinding())

                // RabbitMQ dependencies
                .addModule(new RabbitMQModule())
                .build();
        host.start();
    }

    private static class UserBinding extends AbstractModule {
        @Override
        protected void configure() {
            // Publishers
            MessagePublishers.from(binder())
                    .add(ShipOrderCommand.class, SerializationFormat.JSON)
                    .add(UpdateOrderCommand.class, SerializationFormat.JSON);

            // Listeners
            MessageListeners.from(binder())
                    .add(OrderPlacedEventHandler.class, SerializationFormat.JSON, 50)
                    .add(OrderShippedEventHandler.class, SerializationFormat.JSON, 50);;

            // Retries
            Retries.from(binder()).add(RuntimeException.class, 5000, 1, true);
        }
    }
}
