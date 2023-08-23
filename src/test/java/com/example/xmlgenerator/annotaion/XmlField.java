package com.example.xmlgenerator.annotaion;

import com.example.xmlgenerator.code.YesNoCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlField {

    Type type() default Type.String;

    String name();

    int order() default Integer.MAX_VALUE;

    YesNoCode appendChildYn() default YesNoCode.Y;

    String[] attributeKeys() default {};

    String[] attributeValues() default {};



    enum Type {
        String,
        Enum,
        Date,
        DateTime,
        Class,
        Array
    }
}
