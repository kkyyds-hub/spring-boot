package com.example.studysystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialCategory {
    private Long id;
    private Long userId;
    private String name;
    private Integer sort;
    private LocalDateTime createTime;
}
