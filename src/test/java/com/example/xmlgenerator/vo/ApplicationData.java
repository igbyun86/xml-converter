package com.example.xmlgenerator.vo;

import com.example.xmlgenerator.annotaion.XmlField;
import com.example.xmlgenerator.code.YesNoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ApplicationData {


    @XmlField(name = "공통메타데이터", type = XmlField.Type.Class, order = 1)
    CommonMetaData commonMetaData;

    @XmlField(name = "전자증명서메타데이터", type = XmlField.Type.Array, order = 2)
    List<ElecDocumentMetaData> elecDocumentMetaDataList;


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CommonMetaData {

        @XmlField(name = "신청인", order = 1)
        private String applicant;
        @XmlField(name = "전자문서지갑주소", order = 2)
        private String elecDocumentWalletAddress;
        @XmlField(name = "신청일시", order = 3)
        private LocalDateTime applicationDateTime;
        @XmlField(name = "신청전자증명서", type = XmlField.Type.Array, order = 4, appendChildYn = YesNoCode.N)
        List<ApplicationElecDocument> applicationElecDocumentList;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @XmlField(name = "신청전자증명서", type = XmlField.Type.Array, order = 0)
    public static class ApplicationElecDocument {
        @XmlField(name = "문서종류코드", order = 1)
        private String documentTypeCode;
        @XmlField(name = "문서종류명", order = 2)
        private String documentTypeName;
    }
}
