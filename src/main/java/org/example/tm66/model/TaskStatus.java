package org.example.tm66.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {
    NEW("НОВАЯ", "yellow-mark"),
    WAITING("ОЖИДАЕТ", "olive-mark"),
    CONFIRMED("OK", "green-mark"),
    IN_PROGRESS("В РАБОТЕ", "green-mark"),
    RETURNED("ВОЗВРАЩЕНА", "red-mark"),
    READY("ВЫПОЛНЕНА", "sky-blue-mark");

    private final String labelText;
    private final String css;

    public static TaskStatus get(String s) {
        switch (s) {
            case "Новая":
                return NEW;
            case "Ожидание":
                return WAITING;
            case "Подтверждена":
            case "Подтверждена вторым уровнем":
                return CONFIRMED;
            case "В работе":
                return IN_PROGRESS;
            case "Возвращена":
                return RETURNED;
            case "Выполнена":
            case "Утилизация отчет ДО":
                return READY;
            default:
                throw new IllegalStateException("Не обрабатываемое значение статуса заявки: " + s);
        }
    }

}
