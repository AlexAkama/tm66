package org.example.tm66.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tm66.config.UploadConfig;
import org.example.tm66.model.FinalizeComment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FinalizeCommentService {

    private final ObjectMapper objectMapper;
    private final Path filePath;

    public FinalizeCommentService(ObjectMapper objectMapper, UploadConfig uploadConfig) {
        this.objectMapper = objectMapper;
        filePath = Paths.get(uploadConfig.getCommentDir() + "/finalize_comments.json");
    }

    public void add(FinalizeComment comment) throws IOException {
        List<FinalizeComment> comments = readCommentsFromFile();
        comments.add(comment);
        saveCommentsToFile(comments);
    }

    public Map<String, List<FinalizeComment>> getMapByOrderId() throws IOException {
        return readCommentsFromFile().stream()
                .collect(Collectors.groupingBy(FinalizeComment::getOrderId));
    }

    /**
     * Очистка не используемых комментариев.
     */
    public void clear(Set<String> usedIds) throws IOException {
        List<FinalizeComment> comments = readCommentsFromFile();
        List<FinalizeComment> filtered = comments.stream()
                .filter(comment -> usedIds.contains(comment.getOrderId()))
                .collect(Collectors.toList());
        saveCommentsToFile(filtered);
    }

    private List<FinalizeComment> readCommentsFromFile() throws IOException {
        if (Files.exists(filePath)) {
            return objectMapper.readValue(filePath.toFile(), new TypeReference<List<FinalizeComment>>() {
            });
        } else {
            return new ArrayList<>();
        }
    }

    private void saveCommentsToFile(List<FinalizeComment> comments) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), comments);
    }

}
