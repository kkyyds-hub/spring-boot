package com.example.studysystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialNote {
    private Long id;
    private Long materialId;
    private String createUser;
    private String noteTitle;
    private String noteContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
