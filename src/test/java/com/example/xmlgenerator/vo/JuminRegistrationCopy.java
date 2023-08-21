package com.example.xmlgenerator.vo;

import com.example.xmlgenerator.annotaion.XmlField;
import com.example.xmlgenerator.code.JuminRegistrationCopyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class JuminRegistrationCopy implements ElecDocumentMetaData {
    @XmlField(name = "주소변동사항")
    private JuminRegistrationCopyCode.AddressChangeCode addressChange;
    @XmlField(name = "세대구성사유")
    private String generationCompositionReason;
    @XmlField(name = "세대구성일자")
    private String generationCompositionDate;

    @XmlField(name = "시도시군구선택")
    private District district;


    public static class District {
        @XmlField(name = "sidoNm")
        private String sidoNm;
        @XmlField(name = "siggNm")
        private String siggNm;
        @XmlField(name = "siggCode")
        private String siggCode;
    }


}
