package com.example.studysystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLog {
    private Long id;
    private Long userId;
    private String operator;
    private String operationType;
    private String operationContent;
    private LocalDateTime operationTime;
}
