package com.example.xmlgenerator;

import com.example.xmlgenerator.annotaion.XmlField;
import com.example.xmlgenerator.code.CommonCode;
import com.example.xmlgenerator.code.JuminRegistrationCopyCode;
import com.example.xmlgenerator.code.YesNoCode;
import com.example.xmlgenerator.vo.ApplicationData;
import com.example.xmlgenerator.vo.JuminRegistrationCopy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class XmlConverterTest {
    private static ApplicationData applicationData;

    @BeforeAll
    @DisplayName("신청데이터생성")
    static void applicationDataTest() {
        applicationData = ApplicationData.builder()
                .commonMetaData(ApplicationData.CommonMetaData.builder()
                        .applicant("홍길동")
                        .elecDocumentWalletAddress("123456789012345678901234567890")
                        .applicationDateTime(LocalDateTime.now())
                        .applicationElecDocumentList(
                                List.of(
                                        ApplicationData.ApplicationElecDocument.builder()
                                                .documentTypeCode("30000100001")
                                                .documentTypeName("주민등록표등본")
                                                .build(),
                                        ApplicationData.ApplicationElecDocument.builder()
                                                .documentTypeCode("30000100014")
                                                .documentTypeName("주민등록표초본")
                                                .build()
                                )
                        )
                        .build())
                .elecDocumentMetaDataList(
                        List.of(
                                JuminRegistrationCopy.builder()
                                        .request(JuminRegistrationCopy.Request.builder()
                                                .addressChange(JuminRegistrationCopyCode.AddressChangeCode.NOT_INCLUDED)
                                                .generationCompositionReason("01")
                                                .generationCompositionDate("01")
                                                .build())
                                        .foreignerCode("101")
                                        .district(JuminRegistrationCopy.District.builder()
                                                .sidoNm("서울특별시")
                                                .siggNm("중랑구")
                                                .siggCode("30600003060114중랑구")
                                                .build())
                                        .build()
                        )
                )
                .build();
    }

    @Test
    void objectToXmlConverterTest() throws IllegalAccessException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("신청데이터");
        doc.appendChild(rootElement);
        rootElement.setAttribute("xmlns", "https://www.dpaper.kr/apply/1.0");
        rootElement.setAttribute("xmlns:dpaper", "https://www.dpaper.kr/apply/1.0");

        objectToXmlConverter(applicationData, doc, rootElement);

        writeXml(doc, System.out);
    }

    private void objectToXmlConverter(Object requestObject, Document doc, Element parentElement) throws IllegalAccessException {
        Class<?> requestObjectClass = requestObject.getClass();

        XmlField classXmlField = requestObjectClass.getDeclaredAnnotation(XmlField.class);
        if (!ObjectUtils.isEmpty(classXmlField)) {
            if (classXmlField.appendChildYn() == YesNoCode.Y) {
                Element element = doc.createElement(classXmlField.name());
                parentElement.appendChild(element);

                parentElement = element;
            }
        }

        Field[] fields = requestObjectClass.getDeclaredFields();

        List<Field> fieldList = Arrays.stream(fields)
                .sorted(Comparator.comparing(field -> field.getDeclaredAnnotation(XmlField.class).order()))
                .collect(Collectors.toList());

        for (Field field: fieldList) {
            field.setAccessible(true);
            XmlField xmlFieldAnnotation = field.getDeclaredAnnotation(XmlField.class);
            String name = xmlFieldAnnotation.name();
            Object value = field.get(requestObject);

            if (XmlField.Type.String == xmlFieldAnnotation.type()) {
                Element element = doc.createElement(name);
                element.setTextContent(String.valueOf(value));
                parentElement.appendChild(element);
            }

            if (XmlField.Type.Enum == xmlFieldAnnotation.type()) {
                CommonCode commonCode = (CommonCode) field.get(requestObject);
                System.out.printf("name = %-30s \t code = %s \t name = %s", xmlFieldAnnotation.name(), commonCode.getCode(), commonCode.getName());
                System.out.println();

                Element element = doc.createElement(name);
                element.setTextContent(commonCode.getName());
                element.setAttribute("코드", commonCode.getCode());
                parentElement.appendChild(element);
            }

            if (XmlField.Type.Class == xmlFieldAnnotation.type()) {
                Element element = doc.createElement(name);
                parentElement.appendChild(element);
                objectToXmlConverter(field.get(requestObject), doc, element);
            }

            if (XmlField.Type.Array == xmlFieldAnnotation.type()) {
                List list = (List) field.get(requestObject);

                Element element;
                if (xmlFieldAnnotation.appendChildYn() == YesNoCode.Y) {
                    element = doc.createElement(name);
                    parentElement.appendChild(element);
                } else {
                    element = parentElement;
                }

                list.forEach(d -> {
                    try {
                        objectToXmlConverter(d, doc, element);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

}
