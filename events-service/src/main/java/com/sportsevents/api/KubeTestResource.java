package com.sportsevents.api;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.micrometer.core.annotation.Timed;

@Path("/test-kube")
@RequestScoped
public class KubeTestResource {
    @GET
    @Timed(value="timed.get.testKube.request", histogram=true)
    public String getEvent(){
        return "test-kube works";
    }
}
