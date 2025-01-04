package com.naman.roastmenow.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Theme {

    @JsonProperty("developer")
    DEVELOPER("developer"),

    @JsonProperty("fitness")
    FITNESS("fitness"),

    @JsonProperty("general")
    GENERAL("general");

    private final String value;

    Theme(String value) {
        this.value = value;
    }

    public static Theme fromString(String value) {
        for (Theme theme : Theme.values()) {
            if (theme.getValue().equalsIgnoreCase(value)) {
                return theme;
            }
        }
        return GENERAL;
    }
}
