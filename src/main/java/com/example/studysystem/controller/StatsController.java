package com.example.studysystem.controller;

import com.example.studysystem.service.StatsService;
import com.example.studysystem.vo.ApiResult;
import com.example.studysystem.vo.StatsVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    // 数据统计
    @GetMapping
    public ApiResult<StatsVO> stats() {
        return ApiResult.success(statsService.stats());
    }
}
