package com.example.studysystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploadFile {
    private Long id;
    private Long userId;
    private Long materialId;
    private String originalName;
    private String fileName;
    private String fileUrl;
    private Long fileSize;
    private String uploadUser;
    private LocalDateTime createTime;
}
