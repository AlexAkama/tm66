package org.example.tm66.processor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalizerTest {

    @Test
    void test_1() {
        String address = "\"Свободный, Свободный, Свердловская \", Свободы,38";
        String city = "Свободный";
        String normalized = Normalizer.normalizeAddress(address, city);
        assertEquals("Свободы,38", normalized);
    }

    @Test
    void test_2() {
        String address = "Новоуральск, Новоуральск, Новоуральск г Победы ул 22";
        String city = "Новоуральск";
        String normalized = Normalizer.normalizeAddress(address, city);
        assertEquals("Победы 22", normalized);
    }

    @Test
    void test_3() {
        String point;
        String normalized;

        point = "Пятерочка ООО /";
        normalized = Normalizer.normalizePoint(point);
//        assertEquals("Победы 226", normalized);
        System.out.println(normalized);

        point = "Рябушка ООО / Гурман";
        normalized = Normalizer.normalizePoint(point);
//        assertEquals("Победы 226", normalized);
        System.out.println(normalized);

        point = "А-Оптимус ООО\\АВРОРА";
        normalized = Normalizer.normalizePoint(point);
//        assertEquals("Победы 226", normalized);
        System.out.println(normalized);

    }

}

