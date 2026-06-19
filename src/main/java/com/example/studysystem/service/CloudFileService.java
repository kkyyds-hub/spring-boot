package com.example.studysystem.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudFileService {
    UploadResult upload(MultipartFile file) throws IOException;

    public record UploadResult(String fileName, String fileUrl) {
    }
}
