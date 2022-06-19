package com.subscriptionservice.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum GenderType {

    F("F"),
    M("M"),
    BLANK("");

    private String gender;

    GenderType(final String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    public static GenderType resolveByGender(final String gender) {
        return Arrays.stream(values()).filter(val -> val.getGender().equals(gender)).findFirst().orElse(resolveNoMatch(gender));
    }

    private static GenderType resolveNoMatch(final String gender) {
        GenderType result  = null;
        if (StringUtils.isBlank(gender)) {
            result = BLANK;
        }
        return result;
    }
}
