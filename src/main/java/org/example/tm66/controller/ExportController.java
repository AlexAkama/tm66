package org.example.tm66.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tm66.model.UserParams;
import org.example.tm66.service.IntegrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private final IntegrationService integrationService;

    @GetMapping("/ftp")
    public ResponseEntity<?> toFTP(@RequestParam(name = "user") String user) throws IOException {
        UserParams userParams = integrationService.uploadToFtp(user);
        return ResponseEntity.ok("Успешная загрузка на FTP: <a href='https://pics66.ru" + userParams.getFtpPath() + "'>" + userParams.getName() + "</a>");
    }

    @GetMapping("/static")
    public ResponseEntity<?> save(@RequestParam(name = "user") String user) {
        integrationService.save(user);
        return ResponseEntity.ok("Файл успешно сохранен для " + user);
    }

}
