package com.example.xmlgenerator;

import com.example.xmlgenerator.annotaion.XmlField;
import com.example.xmlgenerator.code.CommonCode;
import com.example.xmlgenerator.code.JuminRegistrationCopyCode;
import com.example.xmlgenerator.vo.ApplicationData;
import com.example.xmlgenerator.vo.JuminRegistrationCopy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class ReflectionTest {

    private final String BLANK = "    ";
    private ApplicationData applicationData;

    @BeforeEach
    void beforeEach() {
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
    void reflectionClass() {
        Field[] fields = JuminInformation.class.getDeclaredFields();
        Arrays.stream(fields)
                .sorted(Comparator.comparing(field -> field.getDeclaredAnnotation(Order.class).value()))
                        .forEach(f -> {
                            f.setAccessible(true);

                            System.out.println("name = " + f.getName());
                            System.out.println("fields = " + f.getType());
                            System.out.println("type = " + f.getType().isAssignableFrom(String.class));
                        });

    }

    @Test
    void reflectionElecDocumentTest() throws IllegalAccessException {
        recursiveReflection(applicationData, 0);

    }

    private void recursiveReflection(Object requestObject, int level) throws IllegalAccessException {
        Class<?> requestObjectClass = requestObject.getClass();

        XmlField classXmlField = requestObjectClass.getDeclaredAnnotation(XmlField.class);
        if (!ObjectUtils.isEmpty(classXmlField)) {
            System.out.printf("%s name = %-30s", levelBlank(level), classXmlField.name());
            System.out.println();
            level++;
        }

        Field[] fields = requestObjectClass.getDeclaredFields();

        List<Field> fieldList = Arrays.stream(fields)
                .sorted(Comparator.comparing(field -> field.getDeclaredAnnotation(XmlField.class).order()))
                .collect(Collectors.toList());

        for (Field field: fieldList) {
            field.setAccessible(true);
            XmlField xmlFieldAnnotation = field.getDeclaredAnnotation(XmlField.class);

            if (XmlField.Type.String == xmlFieldAnnotation.type()) {
                System.out.printf("%s name = %-30s \t value = %s", levelBlank(level), xmlFieldAnnotation.name(), field.get(requestObject));
                System.out.println();
            }

            if (XmlField.Type.Enum == xmlFieldAnnotation.type()) {
                CommonCode commonCode = (CommonCode) field.get(requestObject);
                System.out.printf("%s name = %-30s \t code = %s \t name = %s", levelBlank(level), xmlFieldAnnotation.name(), commonCode.getCode(), commonCode.getName());
                System.out.println();
            }

            if (XmlField.Type.Class == xmlFieldAnnotation.type()) {
                System.out.printf("%s name = %s", levelBlank(level), xmlFieldAnnotation.name());
                System.out.println();
                level++;
                recursiveReflection(field.get(requestObject), level);
            }

            if (XmlField.Type.Array == xmlFieldAnnotation.type()) {
                System.out.printf("%s name = %s", levelBlank(level), xmlFieldAnnotation.name());
                System.out.println();
                List list = (List) field.get(requestObject);
                level++;
                int finalLevel = level;
                list.forEach(d -> {
                    try {
                        recursiveReflection(d, finalLevel);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    private String levelBlank(int level) {
        String levelBlank = "";
        for (int i = 0; i < level; i++) {
            levelBlank += BLANK;
        }

        return levelBlank;
    }



}
