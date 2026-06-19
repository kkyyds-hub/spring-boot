package com.example.studysystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyMaterial {
    private Long id;
    private Long userId;
    private String title;
    private String category;
    private String status;
    private Boolean important;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
