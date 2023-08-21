package com.example.xmlgenerator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

@SpringBootTest
public class ReflectionTest {


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


}
