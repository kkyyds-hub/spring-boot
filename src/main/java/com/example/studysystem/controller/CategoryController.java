package com.example.studysystem.controller;

import com.example.studysystem.dto.CategoryDTO;
import com.example.studysystem.entity.MaterialCategory;
import com.example.studysystem.service.CategoryService;
import com.example.studysystem.vo.ApiResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 分类列表
    @GetMapping
    public ApiResult<List<MaterialCategory>> list() {
        return ApiResult.success(categoryService.list());
    }

    // 新增分类
    @PostMapping
    public ApiResult<Long> add(@RequestBody CategoryDTO dto) {
        return ApiResult.success(categoryService.add(dto));
    }

    // 修改分类
    @PutMapping("/{id}")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        categoryService.update(id, dto);
        return ApiResult.success(null);
    }

    // 删除分类
    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResult.success(null);
    }
}
