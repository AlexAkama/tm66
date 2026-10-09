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

}