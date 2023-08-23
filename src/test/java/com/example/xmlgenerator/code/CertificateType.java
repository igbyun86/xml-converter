package com.example.xmlgenerator.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum CertificateType implements CommonCode {
    JUMIN_REGISTRATION_COPY("3", "주민등록표등본교부"),
    JUMIN_REGISTRATION_ABSTRACT("4", "주민등록표초본");

    private String code;
    private String name;

}
