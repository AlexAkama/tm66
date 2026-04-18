package org.example.tm66.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {
    NEW("НОВАЯ", "yellow-mark"),
    WAITING("ОЖИДАЕТ", "olive-mark"),
    CONFIRMED("OK", "green-mark"),
    RETURNED("ВОЗВРАЩЕНА", "red-mark"),
    READY("ВЫПОЛНЕНА", "sky-blue-mark");

    private final String labelText;
    private final String css;

    public static TaskStatus get(String s) {
        return switch (s) {
            case "Новая" -> NEW;
            case "Ожидание" -> WAITING;
            case "Подтверждена", "Подтверждена вторым уровнем" -> CONFIRMED;
            case "Возвращена" -> RETURNED;
            case "Выполнена", "Утилизация отчет ДО" -> READY;
            default -> throw new IllegalStateException("Не обрабатываемое значение статуса заявки: " + s);
        };
    }

}
