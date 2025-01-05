package com.naman.roastmenow.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @UtilityClass
    public static class Header {
        public static final String X_RATE_LIMIT = "X-RateLimit-Limit";
        public static final String X_RATE_LIMIT__REMAINING = "X-RateLimit-Remaining";
        public static final String X_RATE_LIMIT__RESET = "X-RateLimit-Reset";
    }

}
