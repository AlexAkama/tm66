package org.example.tm66.processor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

import static org.apache.logging.log4j.util.Strings.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WorkValidator {

    private static final Set<String> CARGO = Set.of(
            "Монтаж шкафа",
            "Демонтаж шкафа",
            "Монтаж подвесного шкафа",
            "Демонтаж подвесного шкафа",
            "Монтаж комплекта",
            "Демонтаж комплекта",
            "Монтаж напольного диспенсера",
            "Демонтаж напольного диспенсера"
    );

    private static final String TRASH = "Утилизация оборудования";

    public static boolean isCargo(String work) {
        if (isBlank(work)) {
            return false;
        }
        for (String cargo : CARGO) {
            if (work.startsWith(cargo)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTrash(String work) {
        return TRASH.equals(work);
    }

    public static boolean isAudit(String work) {
        return work.startsWith("Аудит");
    }

    public static boolean isRepair(String work) {
        return work.startsWith("Ремонт");
    }

    public static boolean isElectro(String work) {
        return work.equals("Устранение неполадок с электрикой");
    }

}