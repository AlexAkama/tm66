package org.example.tm66.processor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalizerTest {

    @Test
    void test_address() {
        String address;
        String city;
        String normalized;

        address = "\"Свободный, Свободный, Свердловская \", Свободы,38";
        city = "Свободный";
        normalized = Normalizer.normalizeAddress(address, city);
        assertEquals("Свободы,38", normalized);

        address = "Новоуральск, Новоуральск, Новоуральск г Победы ул 22";
        city = "Новоуральск";
        normalized = Normalizer.normalizeAddress(address, city);
        assertEquals("Победы 22", normalized);

        address = "Сосьва, Североуральск, Коржавина ул. 34";
        city = "Сосьва";
        normalized = Normalizer.normalizeAddress(address, city);
        assertEquals("Коржавина 34", normalized);
    }

    @Test
    void test_city() {
        String city;
        String normalized;

        city = "Сосьва, Североуральск";
        normalized = Normalizer.normalizeCity(city);
        assertEquals("Сосьва", normalized);
    }

    @Test
    void test_point() {
        String point;
        String normalized;

        point = "Пятерочка ООО /";
        normalized = Normalizer.normalizePoint(point);
        assertEquals("Пятерочка ООО", normalized);

        point = "Рябушка ООО / Гурман";
        normalized = Normalizer.normalizePoint(point);
        assertEquals("Рябушка ООО Гурман", normalized);

        point = "А-Оптимус ООО\\АВРОРА";
        normalized = Normalizer.normalizePoint(point);
        assertEquals("А-Оптимус ООО АВРОРА", normalized);

    }

}

