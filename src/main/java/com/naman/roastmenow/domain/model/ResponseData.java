package com.naman.roastmenow.domain.model;

import com.naman.roastmenow.domain.enums.Theme;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ResponseData {
    private Map<Theme, List<String>> compliments;
    private Map<Theme, List<String>> roasts;
}
