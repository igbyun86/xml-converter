package com.example.xmlgenerator.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class JuminRegistrationCopyCode {

    @Getter @AllArgsConstructor
    public enum AddressChangeCode implements CommonCode {
        INCLUDED("01", "포함"),
        NOT_INCLUDED("02", "미포함");

        private final String code;
        private final String name;

    }
}
