package com.redhat.getting.started;

import org.jboss.logging.Logger;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
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
    @Counted(description = "How many prime checks have been performed.")
    @Timed(description = "A measure of how long it takes to perform the primality test.")

    public String hello() {
        registry.counter("hello_count").increment();
        LOG.info("hello");
        return "hello";
    }
}
