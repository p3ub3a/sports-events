package com.sportsevents.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class KubeTestController {
    @GetMapping("/test-kube")
    @Timed(value="timed.get.testKube.request", histogram=true)
    public String getEvent(){
        return "test-kube works";
    }
}
