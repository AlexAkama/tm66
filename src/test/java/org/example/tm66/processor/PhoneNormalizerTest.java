package org.example.tm66.processor;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNormalizerTest {

    @Test
    void test_normalize() {

        String input;
        String normalized;

        input = "81234567890";
        normalized = PhoneNormalizer.normalize(input);
        assertEquals("+71234567890", normalized);

        input = "1234567890";
        normalized = PhoneNormalizer.normalize(input);
        assertEquals("+71234567890", normalized);

        input = "+7(123)-456-78-90";
        normalized = PhoneNormalizer.normalize(input);
        assertEquals("+71234567890", normalized);

        input = "71234567890";
        normalized = PhoneNormalizer.normalize(input);
        assertEquals("+71234567890", normalized);

        input = "+71234567890";
        normalized = PhoneNormalizer.normalize(input);
        assertEquals("+71234567890", normalized);

    }

    @Test
    void test_format() {

        String input;
        String formated;

        input = "12345";
        formated = PhoneNormalizer.format(input);
        assertEquals(input, formated);

        input = "123451234512345";
        formated = PhoneNormalizer.format(input);
        assertEquals(input, formated);

        input = "71234567890";
        formated = PhoneNormalizer.format(input);
        assertEquals("+7 (123) 456-78-90", formated);

        input = "+71234567890";
        formated = PhoneNormalizer.format(input);
        assertEquals("+7 (123) 456-78-90", formated);
    }

    @Test
    void test_addLink() {
        String input;
        String link;

        input = "Оля 12345";
        link = PhoneNormalizer.addTelLink(input);
        assertEquals(input, link);

        input = "Позвоните Васе 123451234512345";
        link = PhoneNormalizer.addTelLink(input);
        assertEquals(input, link);

        input = "1234 51 234512345 Идите Лесом";
        link = PhoneNormalizer.addTelLink(input);
        assertEquals(input, link);

        input = "Контакт +7-123-456-78-90";
        link = PhoneNormalizer.addTelLink(input);
        System.out.println(link);

        input = "Контакт +7 (123) 456 78 90 в точке";
        link = PhoneNormalizer.addTelLink(input);
        System.out.println(link);

        input = "8(123) 456-78-90 позвонить";
        link = PhoneNormalizer.addTelLink(input);
        System.out.println(link);

    }

    @Test
    void test() {
        List<String> list = Arrays.asList(
                "Ляпцева Ольга +79123797231",
                "+79045405486 Шахин",
                "+79222081934",
                "+79089200173",
                "Яна +79122436285",
                "Трекина Татьяна +79221509988"
        );

        for (String s : list) {
            System.out.println(PhoneNormalizer.addTelLink(s));
        }
    }

}