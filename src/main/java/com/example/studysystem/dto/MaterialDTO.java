package com.example.studysystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class MaterialDTO {
    private String title;
    private String category;
    private String status;
    private Boolean important;
    private String content;
    private List<NoteDTO> notes;
}
