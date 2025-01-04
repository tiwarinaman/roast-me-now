package com.naman.roastmenow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naman.roastmenow.domain.enums.ResponseType;
import com.naman.roastmenow.domain.enums.Theme;
import com.naman.roastmenow.domain.model.ResponseData;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ResponseService {

    private Map<Theme, List<String>> complimentsByTheme;
    private Map<Theme, List<String>> roastsByTheme;

    @Value("classpath:responses.json")
    private org.springframework.core.io.Resource resource;

    @PostConstruct
    public void init() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        InputStream inputStream = resource.getInputStream();
        ResponseData responseData = objectMapper.readValue(inputStream, ResponseData.class);

        this.complimentsByTheme = responseData.getCompliments();
        this.roastsByTheme = responseData.getRoasts();
    }

    public Map<String, String> generateComplimentResponse(String name, Theme theme) {
        return generateResponse(ResponseType.COMPLIMENT, name, theme);
    }

    public Map<String, String> generateRoastResponse(String name, Theme theme) {
        return generateResponse(ResponseType.ROAST, name, theme);
    }

    private Map<String, String> generateResponse(ResponseType type, String name, Theme theme) {
        String message = getRandomResponse(type, theme);

        if (name != null) {
            message = name + ", " + message;
        }

        return Map.of(
                "type", type.getValue(),
                "message", message,
                "theme", theme.getValue()
        );
    }

    private String getRandomResponse(ResponseType type, Theme theme) {
        Map<Theme, List<String>> responses = (type == ResponseType.COMPLIMENT) ? complimentsByTheme : roastsByTheme;

        List<String> responsesForTheme = responses.getOrDefault(theme, responses.get(Theme.GENERAL));
        return responsesForTheme.get(new Random().nextInt(responsesForTheme.size()));
    }

}
