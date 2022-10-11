package com.redhat.getting.started;

import java.util.ArrayList;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.logging.Log;

@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @ConfigProperty(name = "cname")
    public String custome;

    private final MeterRegistry registry;
    private ArrayList<Order> orders;

    OrdersResource(MeterRegistry registry) {
        this.registry = registry;
       this.orders= new ArrayList<>();
    }

    @POST
    public Response send(Order order) {
        
        // Return an 202 - Accepted response.
        Log.info("request received: " + order.toString());
        registry.counter("quick_start_orders_total").increment();
        sendOrder(order);
        return Response.accepted().build();
    }

    @GET
    public OrderAggregate getOrders(){
        
        Log.info("Getting orders");
        return new OrderAggregate(orders);
    }

    public void sendOrder(Order order){
        Log.info("Order received");

        //manage wheel and tyre tipes

        switch (order.type) {
            case  "wheel" :
                registry.counter("quick-start-orders-wheels-count").increment();
                Log.info("wheel received");
                orders.add(order);
                break;
            case  "tyre" :
                registry.counter("quick-start-orders-tyres-count").increment();
                Log.info("tyre received");
                orders.add(order);
                break;
            default:
                registry.counter("quick-start-orders-unsupported-count").increment();
                Log.error("unsopported item");
                throw new RuntimeException("Unknow");
        }
    }

}

