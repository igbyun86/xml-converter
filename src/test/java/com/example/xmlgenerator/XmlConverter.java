package com.example.xmlgenerator;

import com.example.xmlgenerator.code.JuminRegistrationCopyCode;
import com.example.xmlgenerator.vo.ApplicationData;
import com.example.xmlgenerator.vo.JuminRegistrationCopy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class XmlConverter {

    @Test
    @DisplayName("신청데이터생성")
    void applicationDataTest() {
        ApplicationData.builder()
                .commonMetaData(ApplicationData.CommonMetaData.builder()
                        .applicant("홍길동")
                        .elecDocumentWalletAddress("123456789012345678901234567890")
                        .applicationDateTime(LocalDateTime.now())
                        .applicationElecDocumentList(
                                List.of(
                                        ApplicationData.ApplicationElecDocument.builder()
                                                .documentTypeCode("30000100001")
                                                .documentTypeName("주민등록표등본")
                                                .build()
                                )
                        )
                        .build())
                .elecDocumentMetaDataList(
                        List.of(
                                JuminRegistrationCopy.builder()
                                        .addressChange(JuminRegistrationCopyCode.AddressChangeCode.NOT_INCLUDED)
                                        .generationCompositionReason("01")
                                        .generationCompositionDate("01")
                                        .build()
                        )
                )
                .build();
    }

}
