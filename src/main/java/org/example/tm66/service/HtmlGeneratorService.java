package org.example.tm66.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tm66.util.TimeUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class HtmlGeneratorService {

    private final OrderService orderService;
    private final SpringTemplateEngine templateEngine;

    public void generateGroupsHtml(String path) {
        Path outputPath = Path.of(path);
        Context context = new Context();
        context.setVariable("groups", orderService.getGroups());
        context.setVariable("nowEnd", orderService.getNowEndOrderId());
        context.setVariable("returned", orderService.getReturnedOrderId());
        context.setVariable("now", TimeUtils.now());
        String html = templateEngine.process("groups", context);
        try {
            Files.writeString(outputPath, html, StandardCharsets.UTF_8);
            log.info("html удачно сформирован и сохранен: {}", path);
        } catch (IOException e) {
            throw new RuntimeException("Не смогли сохранить сгенерированный html", e);
        }
    }

}
