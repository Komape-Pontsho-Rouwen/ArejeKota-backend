package com.pontshocodes.arejekota_backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService {

    //Creating a folder 
    private static final String UPLOAD_DIR = "uploads";

    public String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please choose an image.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed.");
        }

        String originalName = file.getOriginalFilename() == null ? "image" : file.getOriginalFilename();
        String cleanName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String fileName = UUID.randomUUID() + "-" + cleanName;

        try {
            Path folder = Paths.get(UPLOAD_DIR);
            Files.createDirectories(folder);
            Files.copy(file.getInputStream(), folder.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save the image.");
        }

        return "/uploads/" + fileName;
    }

}
