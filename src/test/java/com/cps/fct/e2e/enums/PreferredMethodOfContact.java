package com.cps.fct.e2e.enums;

import lombok.Getter;

@Getter
public enum PreferredMethodOfContact {
    HOME_PHONE(1),
    MOBILE(2),
    EMAIL(3),
    POST(4),
    VIA_POLICE(5),
    ISVA(6);

    private final int value;

    PreferredMethodOfContact(int value) {
        this.value = value;
    }

    public static PreferredMethodOfContact fromValue(int value) {
        for (PreferredMethodOfContact method : PreferredMethodOfContact.values()) {
            if (method.getValue()==value) {
                return method;
            }
        }
        throw new IllegalArgumentException("Incorrect contact method value : " + value);
    }

}
