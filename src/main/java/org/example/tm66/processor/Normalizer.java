package org.example.tm66.processor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Normalizer {

    private static final Map<String, String> CITY_REPLACEMENTS;

    private static final Set<String> ADDRESS_REPLACE_TO_SPACE;
    private static final Set<String> ADDRESS_REMOVE;
    private static final Map<String, String> ADDRESS_REPLACE;

    private static final Map<String, List<String>> ADDRESS_SPECIAL_REMOVE;

    private static final Set<String> POINT_FORM_REMOVE;

    private static final Set<String> REMOVE;

    static {
        CITY_REPLACEMENTS = new HashMap<>();
        CITY_REPLACEMENTS.put("Н. ТАГИЛ", "Нижний Тагил");
        CITY_REPLACEMENTS.put("НИЖНИЙ ТАГИЛ", "Нижний Тагил");
        CITY_REPLACEMENTS.put("ВЕРХНЯЯ СИНЯЧИХА", "Верхняя Синячиха");
        CITY_REPLACEMENTS.put("ВЕРХОТУРЬЕ", "Верхотурье");
        CITY_REPLACEMENTS.put("НОВОУРАЛЬСК, СВЕРДЛОВСКАЯ", "Новоуральск");
        CITY_REPLACEMENTS.put("новоуральск, свердловская", "Новоуральск");
        CITY_REPLACEMENTS.put("Новоуральск, Новоуральск", "Новоуральск");
        CITY_REPLACEMENTS.put("САЛДА, СВЕРДЛОВСКАЯ", "Верхняя Салда");
        CITY_REPLACEMENTS.put("салда, свердловская", "Верхняя Салда");
        CITY_REPLACEMENTS.put("ЮГОРСК", "Югорск");
        CITY_REPLACEMENTS.put("унюган", "Унъюган");
        CITY_REPLACEMENTS.put("белоярский,ханты-мансийский - югра", "Белоярский");
        CITY_REPLACEMENTS.put("КРАСНОТУРИНСК", "Краснотурьинск");
        CITY_REPLACEMENTS.put("СЕРОВ,СВЕРДЛОВСКАЯ", "Серов");
        CITY_REPLACEMENTS.put("Серова", "Серов");
        CITY_REPLACEMENTS.put("СЕВЕРОУРАЛЬСК", "Североуральск");
        CITY_REPLACEMENTS.put("ЦЕМЕНТНЫЙ, СВЕРДЛОВСКАЯ", "Цементный");
        CITY_REPLACEMENTS.put("НИЖНЯЯ ТУРА", "Нижняя Тура");
        CITY_REPLACEMENTS.put("КАРПИНСК", "Карпинск");
        CITY_REPLACEMENTS.put("НИЖНЯЯ САЛДА", "Нижняя Салда");
        CITY_REPLACEMENTS.put("КАЧКАНАР", "Качканар");
        CITY_REPLACEMENTS.put("Качканарн", "Качканар");
        CITY_REPLACEMENTS.put("НЯГАНЬ", "Нягань");
        CITY_REPLACEMENTS.put("УРАЙ,ХАНТЫ-МАНСИЙСКИЙ - ЮГРА", "Урай");
        CITY_REPLACEMENTS.put("ЛЕСНОЙ, СВЕРДЛОВСКАЯ ОБЛ.", "Лесной");
        CITY_REPLACEMENTS.put("СОСВА, СВЕРДЛОВСКАЯ ОБЛ.", "Сосьва");
        CITY_REPLACEMENTS.put("Сосьва, Серовский Р-н", "Сосьва");
        CITY_REPLACEMENTS.put("СОВЕТСКИЙ,Р-н СОВЕТСКИЙ,ХАНТЫ-МАНСИЙСКИЙ - ЮГРА", "Советский");
        CITY_REPLACEMENTS.put("СОВЕТСКИЙ", "Советский");
        CITY_REPLACEMENTS.put("ВОЛЧАНСК", "Волчанск");
        CITY_REPLACEMENTS.put("ВЕРХНИЙ ТАГИЛ", "Верхний Тагил");
        CITY_REPLACEMENTS.put("ПИОНЕРСКИЙ, Р-Н СОВЕТСКИЙ", "Пионерский");
        CITY_REPLACEMENTS.put("ОБЛАСТЬ, ЧЕРЕМУХОВО,СВЕРДЛОВСКАЯ ОБЛАСТЬ", "Черемухово");
        CITY_REPLACEMENTS.put("Черёмухово", "Черемухово");
        CITY_REPLACEMENTS.put("ТАЕЖНЫЙ, Р-Н СОВЕТСКИЙ", "Таежный");
        CITY_REPLACEMENTS.put("НИКОЛО-ПАВЛОВСКОЕ", "Николо-Павловское");
        CITY_REPLACEMENTS.put("Красноуральск, Красноуральск", "Красноуральск");
        CITY_REPLACEMENTS.put("Лесной, Лесной", "Лесной");
        CITY_REPLACEMENTS.put("Пелым, Сведловская область", "Пелым");
        CITY_REPLACEMENTS.put("КАМЕНСК-УРАЛЬСКИЙ", "Каменск-Уральский");
        CITY_REPLACEMENTS.put("Северная, Верхнесалдинский Р-н", "Северная");
        CITY_REPLACEMENTS.put("Кировоград", "Кировград");
        CITY_REPLACEMENTS.put("верхний тагил", "Верхний Тагил");

        ADDRESS_REPLACE_TO_SPACE = new HashSet<>();
        ADDRESS_REPLACE_TO_SPACE.add("ул.");
        ADDRESS_REPLACE_TO_SPACE.add("Ул.");
        ADDRESS_REPLACE_TO_SPACE.add("у.");
        ADDRESS_REPLACE_TO_SPACE.add("пр.");
        ADDRESS_REPLACE_TO_SPACE.add("д.");
        ADDRESS_REPLACE_TO_SPACE.add("с.");
        ADDRESS_REPLACE_TO_SPACE.add("п.");
        ADDRESS_REPLACE_TO_SPACE.add("пгт.");
        ADDRESS_REPLACE_TO_SPACE.add("г.");
        ADDRESS_REPLACE_TO_SPACE.add(" в ");
        ADDRESS_REPLACE_TO_SPACE.add(" ,");
        ADDRESS_REPLACE_TO_SPACE.add("К.");

        ADDRESS_REPLACE = new HashMap<>();
        ADDRESS_REPLACE.put("/", "-");
        ADDRESS_REPLACE.put("\\", "-");
        ADDRESS_REPLACE.put(" км. ", "км");

        ADDRESS_REMOVE = new HashSet<>();
        ADDRESS_REMOVE.add("ул");
        ADDRESS_REMOVE.add("пл");
        ADDRESS_REMOVE.add("пр-кт");
        ADDRESS_REMOVE.add("пр-т");

        ADDRESS_SPECIAL_REMOVE = new HashMap<>();
        ADDRESS_SPECIAL_REMOVE.put("Сосьва", List.of("Серовский Р-н, "));

        POINT_FORM_REMOVE = new HashSet<>();
        POINT_FORM_REMOVE.add("ИП");

        REMOVE = new HashSet<>();
        REMOVE.add("&");
        REMOVE.add("\"");
        REMOVE.add("№");
        REMOVE.add("«");
        REMOVE.add("»");

    }

    public static String normalizeCity(String name) {
        return CITY_REPLACEMENTS.getOrDefault(name, name);
    }

    public static String normalizeAddress(String address, String city) {
        for (String s : ADDRESS_REPLACE_TO_SPACE) {
            address = address.replace(s, " ");
        }
        for (String s : ADDRESS_REMOVE) {
            address = address.replace(s, "");
        }
        address = address.replace(" , ", " ");
        address = address.replaceAll("\\s+", " ");
        address = specialRemove(address, city);
        return address.trim();
    }

    private static String specialRemove(String address, String city) {
        List<String> toRemove = ADDRESS_SPECIAL_REMOVE.get(city);
        if (toRemove != null) {
            for (String s : toRemove) {
                if (address.startsWith(s)) return address.substring(s.length());
            }
        }
        return address;
    }

    public static String normalizeAddressToMail(String address) {
        for (String s : REMOVE) {
            address = address.replace(s, "");
        }

        for (Map.Entry<String, String> entry : ADDRESS_REPLACE.entrySet()) {
            address = address.replace(entry.getKey(), entry.getValue());
        }
        return address;
    }

    public static String normalizePoint(String point) {
        for (String s : REMOVE) {
            point = point.replace(s, "");
        }
        point = point.replaceAll("\\s+", " ");
        return point.trim();
    }

    public static String normalizePointToMail(String point) {
        for (String s : POINT_FORM_REMOVE) {
            point = point.replace(s, "");
        }
        point = point.replace(".", "");
        return point;
    }

    private static String replace2spaseToEmpty(String s, String target) {
        String left = " " + target;
        s = s.replace(left, "");
        String right = target + " ";
        return s.replace(right, "");
    }


}
