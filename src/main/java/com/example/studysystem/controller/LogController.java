package com.example.studysystem.controller;

import com.example.studysystem.entity.OperationLog;
import com.example.studysystem.service.LogService;
import com.example.studysystem.vo.ApiResult;
import com.example.studysystem.vo.PageVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    // 操作日志分页查询
    @GetMapping
    public ApiResult<PageVO<OperationLog>> page(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return ApiResult.success(logService.page(page, pageSize));
    }
}
