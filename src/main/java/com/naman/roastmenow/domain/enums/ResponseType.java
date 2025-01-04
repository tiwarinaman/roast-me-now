package com.naman.roastmenow.domain.enums;

import lombok.Getter;

@Getter
public enum ResponseType {

    COMPLIMENT("compliment"),
    ROAST("roast");

    private final String value;

    ResponseType(String value) {
        this.value = value;
    }

    public static ResponseType fromString(String value) {
        for (ResponseType type : ResponseType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid response type: " + value);
    }
}
