package com.naman.roastmenow.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(HealthCheckApi.HEALTH_CHECK_API)
public interface HealthCheckApi {

    String HEALTH_CHECK_API = "public/ping";

    @GetMapping
    String ping();

}
