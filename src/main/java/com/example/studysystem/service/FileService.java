package com.example.studysystem.service;

import com.example.studysystem.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    UploadFile upload(MultipartFile file, Long materialId) throws IOException;

    void delete(Long id);
}
