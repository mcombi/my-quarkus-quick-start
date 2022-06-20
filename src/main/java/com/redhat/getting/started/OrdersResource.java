package com.redhat.getting.started;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.getting.started.services.OrderService;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.logging.Log;

@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    private final MeterRegistry registry;

    OrdersResource(MeterRegistry registry) {
        this.registry = registry;
    }

    @POST
    public Response send(Order order) {
        
        // Return an 202 - Accepted response.
        Log.info("request received: " + order.toString());
        registry.counter("quick-start-orders-total").increment();
        sendOrder(order);
        return Response.accepted().build();
    }

    public void sendOrder(Order order){
        Log.info("Order received");

        //manage wheel and tyre tipes

        switch (order.type) {
            case  "wheel" :
                registry.counter("quick-start-orders_wheels_count").increment();
                Log.info("wheel received");
                break;
            case  "tyre" :
                registry.counter("quick-start-orders_tyres_count").increment();
                Log.info("tyre received");
                break;
            default:
                registry.counter("quick-start-orders_unsupported_count").increment();
                Log.error("unsopported item");
                throw new RuntimeException("Unknow");
        }
    }

}

