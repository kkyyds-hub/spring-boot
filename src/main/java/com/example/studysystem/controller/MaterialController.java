package com.example.studysystem.controller;

import com.example.studysystem.dto.MaterialDTO;
import com.example.studysystem.entity.StudyMaterial;
import com.example.studysystem.service.MaterialService;
import com.example.studysystem.vo.ApiResult;
import com.example.studysystem.vo.MaterialDetailVO;
import com.example.studysystem.vo.PageVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    // 学习资料分页查询
    @GetMapping
    public ApiResult<PageVO<StudyMaterial>> page(@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 String title,
                                                 String category,
                                                 String status,
                                                 Boolean important,
                                                 String startDate,
                                                 String endDate) {
        return ApiResult.success(materialService.page(page, pageSize, title, category, status, important, startDate, endDate));
    }

    // 学习资料详情
    @GetMapping("/{id}")
    public ApiResult<MaterialDetailVO> detail(@PathVariable Long id) {
        return ApiResult.success(materialService.detail(id));
    }

    // 新增学习资料
    @PostMapping
    public ApiResult<Long> add(@RequestBody MaterialDTO dto) {
        return ApiResult.success(materialService.add(dto));
    }

    // 修改学习资料
    @PutMapping("/{id}")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody MaterialDTO dto) {
        materialService.update(id, dto);
        return ApiResult.success(null);
    }

    // 删除学习资料
    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable Long id) {
        materialService.delete(id);
        return ApiResult.success(null);
    }

    // 批量删除学习资料
    @DeleteMapping("/batch")
    public ApiResult<Void> deleteBatch(@RequestBody List<Long> ids) {
        materialService.deleteBatch(ids);
        return ApiResult.success(null);
    }
}
