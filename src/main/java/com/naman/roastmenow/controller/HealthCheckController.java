package com.naman.roastmenow.controller;

import com.naman.roastmenow.api.HealthCheckApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController implements HealthCheckApi {

    @Override
    public String ping() {
        return "pong 😎";
    }
}
