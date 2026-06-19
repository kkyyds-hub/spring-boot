package com.example.studysystem.service.impl;

import com.example.studysystem.dto.CategoryDTO;
import com.example.studysystem.entity.MaterialCategory;
import com.example.studysystem.mapper.CategoryMapper;
import com.example.studysystem.service.CategoryService;
import com.example.studysystem.service.LogService;
import com.example.studysystem.utils.UserContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final LogService logService;

    public CategoryServiceImpl(CategoryMapper categoryMapper, LogService logService) {
        this.categoryMapper = categoryMapper;
        this.logService = logService;
    }

    @Override
    public List<MaterialCategory> list() {
        return categoryMapper.list();
    }

    @Override
    public Long add(CategoryDTO dto) {
        MaterialCategory category = new MaterialCategory();
        category.setUserId(UserContext.getUserId());
        category.setName(dto.getName());
        category.setSort(dto.getSort() == null ? 0 : dto.getSort());
        category.setCreateTime(LocalDateTime.now());
        categoryMapper.insert(category);
        logService.record("新增分类", "新增资料分类：" + category.getName());
        return category.getId();
    }

    @Override
    public void update(Long id, CategoryDTO dto) {
        MaterialCategory category = new MaterialCategory();
        category.setId(id);
        category.setUserId(UserContext.getUserId());
        category.setName(dto.getName());
        category.setSort(dto.getSort() == null ? 0 : dto.getSort());
        categoryMapper.update(category);
        logService.record("修改分类", "修改资料分类：" + category.getName());
    }

    @Override
    public void delete(Long id) {
        categoryMapper.deleteById(id);
        logService.record("删除分类", "删除资料分类：" + id);
    }
}
