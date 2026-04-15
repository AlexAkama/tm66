package org.example.tm66.processor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Locations {

    private static final Map<String, String> LOCATIONS;

    static {
        LOCATIONS = new HashMap<>();
        LOCATIONS.put("Склад", "[-]");
        LOCATIONS.put("Нижний Тагил", "[0]");
        LOCATIONS.put("Николо-Павловское", "[1]->S0");
        LOCATIONS.put("Шиловка", "[1]->S0->E-(1)");
        LOCATIONS.put("Новоасбест", "[1]->S0->E-(2)");
        LOCATIONS.put("Невьянск", "[1]->S1-(1)");
        LOCATIONS.put("Быньги", "[1]->S1-(1)->NE");
        LOCATIONS.put("Цементный", "[1]->S1-(2)");
        LOCATIONS.put("Кировград", "[1]->S1-(3)");
        LOCATIONS.put("Карпушиха", "[1]->S1-(3)->NW");
        LOCATIONS.put("Левиха", "[1]->S1-(3)->NW");
        LOCATIONS.put("Верхний Тагил", "[1]->S1-(4)");
        LOCATIONS.put("Верх-Нейвинский", "[1]->S2-(1)");
        LOCATIONS.put("Новоуральск", "[1]->S2-(2)*");
        LOCATIONS.put("Нейво-Рудянка", "[1]->S2-(3)");
        LOCATIONS.put("Калиново", "[1]->S3-(1)");
        LOCATIONS.put("Тарасково", "[1]->S3-(2)");
        LOCATIONS.put("Таватуй", "[1]->S3-(3)");
        LOCATIONS.put("Черноисточинск", "[1]->SW-(1)");
        LOCATIONS.put("Уралец", "[1]->SW-(2)");
        LOCATIONS.put("Висимо-Уткинск", "[1]->SW-(3)");
        LOCATIONS.put("Покровское", "[2]->E1-(1)");
        LOCATIONS.put("Северная", "[2]->E1-(2)");
        LOCATIONS.put("Свободный", "[2]->E1-(3)*");
        LOCATIONS.put("Верхняя Салда", "[2]->E1-(4)");
        LOCATIONS.put("Нижняя Салда", "[2]->E1-(5)");
        LOCATIONS.put("Басьяновский", "[2]->E1-(5)-N");
        LOCATIONS.put("Петрокаменское", "[2]->E2-(1)");
        LOCATIONS.put("Нейво-Шайтанский", "[2]->E2-(2)");
        LOCATIONS.put("Алапаевск", "[2]->E2-(3)");
        LOCATIONS.put("Заря(Алапаевск)", "[2]->E2-(3)");
        LOCATIONS.put("Верхняя Синячиха", "[2]->E2-(4)");
        LOCATIONS.put("Невьянское", "[2]->E2-(4)-W");
        LOCATIONS.put("Арамашево", "[2]->E2-(5)");
        LOCATIONS.put("Кировское", "[2]->E2-(5)");
        LOCATIONS.put("Курорт-Самоцвет", "[2]->E2-(6)");
        LOCATIONS.put("Евстюниха", "[3]->N1-(0)");
        LOCATIONS.put("Большая Лая", "[3]->N1-(1)");
        LOCATIONS.put("Горноуральский", "[3]->N1-(1)");
        LOCATIONS.put("Малая Лая", "[3]->N1-(2)");
        LOCATIONS.put("Кушва", "[3]->N1-(3)");
        LOCATIONS.put("Баранчинский", "[3]->N1-(4)");
        LOCATIONS.put("Красноуральск", "[3]->N1-(5)");
        LOCATIONS.put("Верхняя Тура", "[3]->N1-(6)");
        LOCATIONS.put("Теплая Гора", "[3]->N1-(6)->W");
        LOCATIONS.put("Нижняя Тура", "[3]->N2-(1)");
        LOCATIONS.put("Лесной", "[3]->N2-(2)*");
        LOCATIONS.put("Валериановск", "[3]->N2-(3)");
        LOCATIONS.put("Качканар", "[3]->N2-(4)");
        LOCATIONS.put("Ис", "[3]->N2-(5)");
        LOCATIONS.put("Привокзальный", "[3]->N3-(1)");
        LOCATIONS.put("Верхотурье", "[3]->N3-(2)");
        LOCATIONS.put("Кордюково", "[3]->N3-(2)-E-(1)");
        LOCATIONS.put("Восточный", "[3]->N3-(2)-E-(2)");
        LOCATIONS.put("Новая Ляля", "[3]->N3-(3)");
        LOCATIONS.put("Лобва", "[3]->N3-(4)");
        LOCATIONS.put("Красноярка", "[3]->N3-(5)");
        LOCATIONS.put("Серов", "[3]->N4");
        LOCATIONS.put("Сосьва", "[3]->N4->SE");
        LOCATIONS.put("Гари", "[3]->N4->SE->NE");
        LOCATIONS.put("Кытлым", "[3]->N4->W");
        LOCATIONS.put("Рудничный", "[3]->N5-(0)");
        LOCATIONS.put("Карпинск", "[3]->N5-(1)");
        LOCATIONS.put("Краснотурьинск", "[3]->N5-(2)");
        LOCATIONS.put("Волчанск", "[3]->N5-(3)");
        LOCATIONS.put("Североуральск", "[3]->N6-(1)");
        LOCATIONS.put("Покровск-Уральский", "[3]->N6-(1)-W1");
        LOCATIONS.put("Баяновка", "[3]->N6-(1)-W2");
        LOCATIONS.put("Третий Северный", "[3]->N6-(2)");
        LOCATIONS.put("Калья", "[3]->N6-(3)");
        LOCATIONS.put("Черемухово", "[3]->N6-(4)");
        LOCATIONS.put("Ивдель", "[3]->N7");
        LOCATIONS.put("Полуночное", "[3]->N7->N");
        LOCATIONS.put("Пелым", "[3]->N7->NE1-(0)");
        LOCATIONS.put("Таежный", "[3]->N7->NE1-(1)");
        LOCATIONS.put("Алябьевский", "[3]->N7->NE1-(2)");
        LOCATIONS.put("Малиновский", "[3]->N7->NE1-(3)");
        LOCATIONS.put("Пионерский", "[3]->N7->NE1-(4)");
        LOCATIONS.put("Юбилейный", "[3]->N7->NE1-(5)");
        LOCATIONS.put("Югорск", "[3]->N7->NE2-(2)");
        LOCATIONS.put("Агириш", "[3]->N7->NE2-(2)->N");
        LOCATIONS.put("Советский", "[3]->N7->NE2-(3)");
        LOCATIONS.put("Урай", "[3]->N7->NE2-(3)->SSE-(0)");
        LOCATIONS.put("Междуреченский", "[3]->N7->NE2-(3)->SSE-(1)");
        LOCATIONS.put("Мортка", "[3]->N7->NE2-(3)->SSE-(2)");
        LOCATIONS.put("Зеленоборск", "[3]->N7->NE4");
        LOCATIONS.put("Коммунистический", "[3]->N7->NE5");
        LOCATIONS.put("Унъюган", "[3]->N7->NE6");
        LOCATIONS.put("Нягань", "[3]->N7->NE7");
        LOCATIONS.put("Игрим", "[3]->N7->NE7->N1");
        LOCATIONS.put("Березово", "[3]->N7->NE7->N2");
        LOCATIONS.put("Талинка", "[3]->N7->NE7->SSE-(0)");
        LOCATIONS.put("Сергино", "[3]->N7->NE8-(1)");
        LOCATIONS.put("Приобье", "[3]->N7->NE8-(2)");
        LOCATIONS.put("Перегребное", "[3]->N7->NE9-(1)");
        LOCATIONS.put("Белоярский", "[3]->N7->NE9-(2)");
        LOCATIONS.put("Сорум", "[3]->N7->NE9-(3)");
    }

    public static String getLocation(String city) {
        return LOCATIONS.getOrDefault(city, "");
    }

}
