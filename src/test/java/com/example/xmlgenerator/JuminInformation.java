package com.example.xmlgenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class JuminInformation {

    @Order(1)
    private String gubun;

    @Order(3)
    private RequestInformation requestInformation;

    @Order(2)
    private String domesticYn;


    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class RequestInformation {
        private String jusoChangeInformation;
        private String sedegusungReason;
        private String sedegusungDate;
    }

}
