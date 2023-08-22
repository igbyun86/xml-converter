package com.example.xmlgenerator.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum CertificateType implements CommonCode {
    JUMIN_REGISTRATION_COPY("3", "주민등록표등본교부");

    private String code;
    private String name;

}
