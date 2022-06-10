package com.redhat.getting.started;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;

import io.quarkus.logging.Log;

@Path("/hello1")
public class TracedResource {

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("sayHello/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@PathParam("name") String name) {

        Span span = Span.current();

        span.setAttribute("event", name);
        span.setAttribute("message", "this is a log message for name " + name);

        String response = formatGreeting(name);
        span.setAttribute("response", response);

        return response;
    }

    @GET
    @Path("sayRemote/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayRemote(@PathParam("name") String name) {

        Span span = Span.current();

        span.setAttribute("event", name);
        span.setAttribute("message", "this is a log message for name " + name);

        String serviceName = System.getenv("SERVICE_NAME");
        if (serviceName == null) {
            serviceName = uriInfo.getBaseUri().toString();
        }

        //Log.info("Uri: " + uriInfo.getBaseUri());
        //Log.info(serviceName);
        URL myURL = null;
        try {
            myURL = new URL(serviceName);
        } catch (MalformedURLException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        ResourceClient resourceClient = RestClientBuilder.newBuilder()
                .baseUrl(myURL)
                //.baseUri(uriInfo.getBaseUri())
                .build(ResourceClient.class);
        String response = resourceClient.hello(name) + " from " + serviceName;
        span.setAttribute("response", response);

        return response;
    }

    private String formatGreeting(String name) {
        Span span = Span.current();
        span.addEvent("formatGreeting", Attributes.of(AttributeKey.stringKey("text"),name));

        String response = "hello: " + name;
        span.addEvent("done", Attributes.of(AttributeKey.stringKey("respone"),response));

        return response;
    }

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        //Log.info(System.getenv("OTELCOL_SERVER"));
        Log.info("hello");
        return "hello";
    }

    @GET
    @Path("hello/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@PathParam("name") String name) {
        Span span = Span.current();
        span.setAttribute("name", name);
        Log.info("hello: " + name);
        return "hello: " + name;
    }

    @GET
    @Path("chain")
    @Produces(MediaType.TEXT_PLAIN)
    public String chain() {
        Log.info("Uri: " + uriInfo.getBaseUri());
        ResourceClient resourceClient = RestClientBuilder.newBuilder()
                .baseUri(uriInfo.getBaseUri())
                .build(ResourceClient.class);
        return "chain -> " + resourceClient.hello("test");
    }
}