package com.redhat.getting.started.services;

import io.micrometer.core.instrument.MeterRegistry;
import com.redhat.getting.started.Order;
import io.quarkus.logging.Log;

public class OrderService {
    private final MeterRegistry registry;

    public OrderService(MeterRegistry registry){
        this.registry=registry;
    }

    public void sendOrder(Order order){
        Log.info("Order received");

        //manage wheel and tyre tipes

        switch (order.type) {
            case  "wheel" :
                registry.counter("orders_wheels_count").increment();
                break;
            case  "tyre" :
                registry.counter("orders_tyres_count").increment();
                break;
            default:
                registry.counter("orders_unknown_count").increment();
                throw new RuntimeException("Unknow");
        }
    }
}
