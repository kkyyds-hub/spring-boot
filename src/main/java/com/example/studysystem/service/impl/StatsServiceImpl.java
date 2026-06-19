package com.example.studysystem.service.impl;

import com.example.studysystem.mapper.MaterialMapper;
import com.example.studysystem.service.StatsService;
import com.example.studysystem.vo.StatsVO;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {
    private final MaterialMapper materialMapper;

    public StatsServiceImpl(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    @Override
    public StatsVO stats() {
        return new StatsVO(materialMapper.countByCategory(), materialMapper.countByMonth(), materialMapper.countByStatus());
    }
}
