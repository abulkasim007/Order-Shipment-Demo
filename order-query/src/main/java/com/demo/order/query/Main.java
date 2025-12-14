package com.demo.order.query;

import com.demo.order.query.event.handlers.OrderCompletedEventHandler;
import com.google.inject.AbstractModule;
import libs.axiom.data.abstractions.exceptions.ConcurrencyException;
import libs.axiom.data.mongodb.implementations.MongoDbModule;
import libs.axiom.host.implementations.builder.Host;
import libs.axiom.host.implementations.builder.HostBuilder;
import libs.axiom.messaging.abstractions.MessageListeners;
import libs.axiom.messaging.abstractions.Retries;
import libs.axiom.messaging.rabbitmq.implementations.RabbitMQModule;
import libs.axiom.serialization.abstractions.SerializationFormat;

public class Main {

    static void main(String[] args) {

        Host host = new HostBuilder()
                // User provided services
                .addModule(new UserBinding())

                // MongoDB dependencies
                .addModule(new MongoDbModule())

                // RabbitMQ dependencies
                .addModule(new RabbitMQModule())
                .build();
        host.start();
    }

    private static class UserBinding extends AbstractModule {
        @Override
        protected void configure() {

            // Listeners
            MessageListeners.from(binder())
                    .add(OrderCompletedEventHandler.class, SerializationFormat.JSON, 50);

            // Retries
            Retries.from(binder()).add(ConcurrencyException.class, 5000, 1, true);
        }
    }
}
