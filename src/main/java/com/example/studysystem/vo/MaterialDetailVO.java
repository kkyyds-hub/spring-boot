package com.example.studysystem.vo;

import com.example.studysystem.entity.MaterialNote;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MaterialDetailVO {
    private Long id;
    private String title;
    private String category;
    private String status;
    private Boolean important;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<MaterialNote> notes;
    private List<com.example.studysystem.entity.UploadFile> files;
}
