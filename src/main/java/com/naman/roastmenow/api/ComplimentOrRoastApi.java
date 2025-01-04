package com.naman.roastmenow.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping(ComplimentOrRoastApi.BASE_API_V1)
public interface ComplimentOrRoastApi {

    String BASE_API_V1 = "/api/v1";
    String GENERATE_COMPLEMENT_API = "/compliment";
    String GENERATE_ROAST_API = "/roast";
    String GENERATE_RANDOM_API = "/random";

    @GetMapping(GENERATE_COMPLEMENT_API)
    ResponseEntity<Map<String, String>> generateCompliment(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "general") String theme
    );

    @GetMapping(GENERATE_ROAST_API)
    ResponseEntity<Map<String, String>> generateRoast(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "general") String theme
    );

    @GetMapping(GENERATE_RANDOM_API)
    ResponseEntity<Map<String, String>> generateRandom(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "general") String theme
    );

}
