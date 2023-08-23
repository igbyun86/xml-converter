package com.example.xmlgenerator.vo;

import com.example.xmlgenerator.annotaion.XmlField;
import com.example.xmlgenerator.code.CertificateType;
import com.example.xmlgenerator.code.JuminRegistrationCopyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@XmlField(name = "IN-주민등록표초본신청서", type = XmlField.Type.Class, order = 0)
public class JuminRegistrationAbstract implements ElecDocumentMetaData {
    @XmlField(name = "구분", type = XmlField.Type.Enum, order = 1)
    private static final CertificateType certificateType = CertificateType.JUMIN_REGISTRATION_ABSTRACT;

    @XmlField(name = "신청내용", type = XmlField.Type.Class, order = 2)
    private Request request;

    @XmlField(name = "외국인여부", type = XmlField.Type.String, order = 3)
    private String foreignerCode;

    @XmlField(name = "시도시군구선택", type = XmlField.Type.Class, order = 4)
    private District district;

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Request {
        @XmlField(name = "주소변동사항", type = XmlField.Type.Enum, order = 1)
        private JuminRegistrationCopyCode.AddressChangeCode addressChange;
        @XmlField(name = "세대구성사유", order = 2)
        private String generationCompositionReason;
        @XmlField(name = "세대구성일자", order = 3)
        private String generationCompositionDate;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class District {
        @XmlField(name = "sidoNm", order = 1)
        private String sidoNm;
        @XmlField(name = "siggNm", order = 2)
        private String siggNm;
        @XmlField(name = "siggCode", order = 3)
        private String siggCode;
    }


}
