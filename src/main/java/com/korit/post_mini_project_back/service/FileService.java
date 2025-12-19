package com.korit.post_mini_project_back.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FileService {

    @Value("${user.dir}")
    private String projectPath;

    public Map<String, String > upload(String path, List<MultipartFile> files) {
        Map<String , String> fileMap = new LinkedHashMap<>();

        if (files == null || files.isEmpty()) {
            return null;
        }

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String newFilename = UUID.randomUUID().toString() + "_" + originalFileName;
            String extension = originalFileName.substring(originalFileName.lastIndexOf(".")  + 1); // 확장자 .jpg .txt 이런거
            Path uploadDirPath = Paths.get(projectPath, "upload", path);
            try {
                // 경로가 존재하지 않으면 자동으로 전체 폴더 경로 생성
                Files.createDirectories(uploadDirPath);
            } catch (IOException e) {}
            Path filePath = uploadDirPath.resolve(newFilename);
            try {
                file.transferTo(filePath);
            } catch (IOException e) {}
        }

        return fileMap;
    }

}
