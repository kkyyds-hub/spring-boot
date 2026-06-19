package com.example.studysystem.service;

import com.example.studysystem.dto.CategoryDTO;
import com.example.studysystem.entity.MaterialCategory;

import java.util.List;

public interface CategoryService {
    List<MaterialCategory> list();

    Long add(CategoryDTO dto);

    void update(Long id, CategoryDTO dto);

    void delete(Long id);
}
