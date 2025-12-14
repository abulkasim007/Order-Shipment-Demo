package com.demo.shipment.service;

import com.demo.shipment.app.ShipOrderCommandHandler;
import com.demo.shipment.domain.ShipmentAggregateRoot;
import com.demo.shipment.events.OrderShippedEvent;
import com.google.inject.AbstractModule;
import libs.axiom.data.abstractions.AggregateRootRepositories;
import libs.axiom.data.abstractions.DatabaseType;
import libs.axiom.data.abstractions.exceptions.ConcurrencyException;
import libs.axiom.data.abstractions.reliability.OutboxImpl;
import libs.axiom.data.mongodb.implementations.MongoDbModule;
import libs.axiom.host.implementations.builder.Host;
import libs.axiom.host.implementations.builder.HostBuilder;
import libs.axiom.messaging.abstractions.MessageListeners;
import libs.axiom.messaging.abstractions.MessagePublishers;
import libs.axiom.messaging.abstractions.Retries;
import libs.axiom.messaging.abstractions.UserContext;
import libs.axiom.messaging.rabbitmq.implementations.RabbitMQModule;
import libs.axiom.serialization.abstractions.SerializationFormat;

import java.util.List;
import java.util.Map;

public class Main {

    static void main(String[] args) {

        Host host = new HostBuilder()
                // User provided services
                .addModule(new UserBinding())

                // MongoDB dependencies
                .addModule(new MongoDbModule())

                // RDBMS dependencies
                //.addModule(new RdbmsModule(RdbmsEntityMaps.Maps))

                // RabbitMQ dependencies
                .addModule(new RabbitMQModule())
                .build();
        host.start();
    }

    private static class UserBinding extends AbstractModule {
        @Override
        protected void configure() {
            // Publishers
            MessagePublishers.from(binder()).add(OrderShippedEvent.class, SerializationFormat.JSON);

            // Listeners
            MessageListeners.from(binder()).add(ShipOrderCommandHandler.class, SerializationFormat.JSON, 50);

            // Retries
            Retries.from(binder()).add(ConcurrencyException.class, 5000, 1, true);

            // Aggregate root repositories
            AggregateRootRepositories.from(binder())
                    .add(ShipmentAggregateRoot.class)
                    .withOutbox();
        }
    }

    private static class RdbmsEntityMaps {
        public static Map<DatabaseType, List<Class<?>>> Maps = Map.of(
                DatabaseType.STATE,
                List.of(OrderShippedEvent.class, UserContext.class, OutboxImpl.class, ShipmentAggregateRoot.class),
                DatabaseType.EVENT,
                List.of(OrderShippedEvent.class, UserContext.class));
    }


}

