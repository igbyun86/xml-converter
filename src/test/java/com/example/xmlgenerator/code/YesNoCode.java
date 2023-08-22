package com.example.xmlgenerator.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum YesNoCode implements CommonCode {
    Y("Y", "Y"),
    N("N", "N");

    private String code;
    private String name;
}
