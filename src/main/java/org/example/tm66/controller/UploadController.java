package org.example.tm66.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.tm66.model.FinalizeComment;
import org.example.tm66.model.TrashEquipment;
import org.example.tm66.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final OrderService orderService;
    private final TrashOrderService trashOrderService;
    private final FinalizeCommentService finalizeCommentService;
    private final IntegrationService integrationService;

    @PostMapping("tasks")
    public ResponseEntity<?> uploadXlsx(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл не выбран");
        }
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            orderService.process(workbook);
            return ResponseEntity.ok("Файл успешно обработан");
        } catch (Exception e) {
            log.error("Ошибка при обработке файла:", e);
            return ResponseEntity.internalServerError().body("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    @PostMapping("/trash/file")
    public ResponseEntity<?> uploadTrashContentFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return
                    ResponseEntity.badRequest().body("Файл не выбран");
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            List<String> lines = reader.lines().toList();
            trashOrderService.update(lines);

            return ResponseEntity.ok("Файл успешно обработан");
        } catch (Exception e) {
            log.error("Ошибка при обработке файла:", e);
            return ResponseEntity.internalServerError().body("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    @PostMapping("/trash")
    public ResponseEntity<?> uploadTrashContent(@ModelAttribute TrashEquipment equipment) {
        try {
            List<String> data = new ArrayList<>();
            data.add(equipment.orderId());
            String[] split = equipment.equipment().split("\n");
            data.addAll(Arrays.stream(split).toList());
            trashOrderService.update(data);
            return ResponseEntity.ok("Оборудование для утилизации успешно добавлен");
        } catch (Exception e) {
            log.error("Ошибка при добавлении оборудования для утилизации:", e);
            return ResponseEntity.internalServerError().body("Ошибка при добавлении комментария: " + e.getMessage());
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> uploadComment(@ModelAttribute FinalizeComment comment) {
        try {
            finalizeCommentService.add(comment);
            return ResponseEntity.ok("Комментарий успешно добавлен");
        } catch (Exception e) {
            log.error("Ошибка при добавлении комментария:", e);
            return ResponseEntity.internalServerError().body("Ошибка при добавлении комментария: " + e.getMessage());
        }
    }

    @GetMapping("/ftp")
    public ResponseEntity<?> tpFTP(@RequestParam(name = "user") String user) throws IOException {
        integrationService.upload(user);
        return ResponseEntity.ok("Успешная загрузка на FTP");
    }

}
