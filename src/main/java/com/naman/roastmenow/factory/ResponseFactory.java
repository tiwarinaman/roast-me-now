package com.naman.roastmenow.factory;

import com.naman.roastmenow.domain.enums.ResponseType;
import com.naman.roastmenow.domain.enums.Theme;
import com.naman.roastmenow.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResponseFactory {

    private final ResponseService responseService;

    public Map<String, String> generateResponse(ResponseType type, String name, Theme theme) {
        return switch (type) {
            case COMPLIMENT -> responseService.generateComplimentResponse(name, theme);
            case ROAST -> responseService.generateRoastResponse(name, theme);
        };
    }

}
