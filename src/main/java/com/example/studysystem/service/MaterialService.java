package com.example.studysystem.service;

import com.example.studysystem.dto.MaterialDTO;
import com.example.studysystem.entity.StudyMaterial;
import com.example.studysystem.vo.MaterialDetailVO;
import com.example.studysystem.vo.PageVO;

import java.util.List;

public interface MaterialService {
    Long add(MaterialDTO dto);

    void update(Long id, MaterialDTO dto);

    void delete(Long id);

    void deleteBatch(List<Long> ids);

    MaterialDetailVO detail(Long id);

    PageVO<StudyMaterial> page(Integer page, Integer pageSize, String title, String category, String status, Boolean important, String startDate, String endDate);
}
