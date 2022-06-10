//package com.redhat.getting.started;
//
//import io.opentelemetry.sdk.trace.IdGenerator;
//import io.opentelemetry.sdk.trace.samplers.Sampler;
//
//
//import javax.inject.Singleton;
//import javax.ws.rs.Produces;
//
//@Singleton
//public class CustomConfiguration {
//    /** Creates a custom sampler for OpenTelemetry */
//    @Produces
//    @Singleton
//    public Sampler sampler() {
//        return JaegerRemoteSampler.builder()
//                .setServiceName("my-service")
//                .build();
//    }
//}
