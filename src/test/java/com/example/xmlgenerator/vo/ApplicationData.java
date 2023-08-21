package com.example.xmlgenerator.vo;

import com.example.xmlgenerator.annotaion.XmlField;
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

    @XmlField(name = "공통메타데이터")
    CommonMetaData commonMetaData;
    @XmlField(name = "전자증명서메타데이터")
    List<ElecDocumentMetaData> elecDocumentMetaDataList;


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CommonMetaData {
        @XmlField(name = "신청인")
        private String applicant;
        @XmlField(name = "전자문서지갑주소")
        private String elecDocumentWalletAddress;
        @XmlField(name = "신청일시")
        private LocalDateTime applicationDateTime;
        @XmlField(name = "신청전자증명서")
        List<ApplicationElecDocument> applicationElecDocumentList;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ApplicationElecDocument {
        @XmlField(name = "문서종류코드")
        private String documentTypeCode;
        @XmlField(name = "문서종류명")
        private String documentTypeName;
    }
}
