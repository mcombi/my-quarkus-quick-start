package com.redhat.getting.started;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        registry.counter("quick-start-orders").increment();
        return Response.accepted().build();
    }

}

