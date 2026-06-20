package org.example.tm66.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tm66.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/clear")
@RequiredArgsConstructor
public class ClearController {

    private final OrderService orderService;

    @PostMapping("/trash")
    public ResponseEntity<?> clearTrash() {
        try {
            orderService.clearTrash();
            return ResponseEntity.ok("Оборудование для утилизации успешно очищено");
        } catch (Exception e) {
            log.error("Ошибка при очистке оборудования для утилизации:", e);
            return ResponseEntity.internalServerError().body("Ошибка при очистке комментария: " + e.getMessage());
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> clearComment() {
        try {
            orderService.clearComment();
            return ResponseEntity.ok("Комментарии успешно очищены");
        } catch (Exception e) {
            log.error("Ошибка при очистке комментария:", e);
            return ResponseEntity.internalServerError().body("Ошибка при очистке комментария: " + e.getMessage());
        }
    }


}
