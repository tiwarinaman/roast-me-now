package com.naman.roastmenow.controller;

import com.naman.roastmenow.api.ComplimentOrRoastApi;
import com.naman.roastmenow.domain.enums.ResponseType;
import com.naman.roastmenow.domain.enums.Theme;
import com.naman.roastmenow.factory.ResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ComplimentOrRoastController implements ComplimentOrRoastApi {

    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<Map<String, String>> generateCompliment(String name, String theme) {
        try {
            Theme selectedTheme = Theme.fromString(theme);
            Map<String, String> response = responseFactory.generateResponse(ResponseType.COMPLIMENT, name, selectedTheme);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", "Invalid theme provided"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> generateRoast(String name, String theme) {
        try {
            Theme selectedTheme = Theme.fromString(theme);
            Map<String, String> response = responseFactory.generateResponse(ResponseType.ROAST, name, selectedTheme);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", "Invalid theme provided"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> generateRandom(String name, String theme) {
        try {
            Theme selectedTheme = Theme.fromString(theme);
            ResponseType type = Math.random() > 0.5 ? ResponseType.COMPLIMENT : ResponseType.ROAST;
            Map<String, String> response = responseFactory.generateResponse(type, name, selectedTheme);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", "Invalid theme provided"), HttpStatus.BAD_REQUEST);
        }
    }

}
