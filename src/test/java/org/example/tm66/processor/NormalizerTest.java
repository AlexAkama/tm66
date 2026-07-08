package org.example.tm66.processor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalizerTest {

    @Test
    void test() {
        String address = "\"Свободный, Свободный, Свердловская \", Свободы,38";
        String city = "Свободный";
        String normalized = Normalizer.normalizeAddress(address, city);
        assertEquals("Свободы,38", normalized);
    }

}