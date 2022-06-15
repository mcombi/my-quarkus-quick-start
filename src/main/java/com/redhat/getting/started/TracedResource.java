package com.redhat.getting.started;

import org.jboss.logging.Logger;

import io.micrometer.core.instrument.MeterRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class TracedResource {

    private static final Logger LOG = Logger.getLogger(TracedResource.class);
    private final MeterRegistry registry;

    TracedResource(MeterRegistry registry) {
        this.registry = registry;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        registry.counter("hello.count").increment();
        LOG.info("hello");
        return "hello";
    }
}
