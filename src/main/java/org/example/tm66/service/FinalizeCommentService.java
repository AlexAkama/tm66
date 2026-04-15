package org.example.tm66.service;


import org.example.tm66.model.FinalizeComment;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinalizeCommentService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path filePath = Paths.get("upload/finalize_comments.json");

    public void add(FinalizeComment comment) {
        List<FinalizeComment> comments = readCommentsFromFile();
        comments.add(comment);
        saveCommentsToFile(comments);
    }

    public Map<String, List<FinalizeComment>> getMapByOrderId() {
        return readCommentsFromFile().stream()
                .collect(Collectors.groupingBy(FinalizeComment::orderId));
    }

    private List<FinalizeComment> readCommentsFromFile() {
        if (Files.exists(filePath)) {
            return objectMapper.readValue(filePath.toFile(), new TypeReference<>() {
            });
        } else {
            return new ArrayList<>();
        }
    }

    private void saveCommentsToFile(List<FinalizeComment> comments) {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), comments);
    }

}
