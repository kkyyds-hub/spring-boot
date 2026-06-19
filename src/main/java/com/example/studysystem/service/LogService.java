package com.example.studysystem.service;

import com.example.studysystem.entity.OperationLog;
import com.example.studysystem.vo.PageVO;

public interface LogService {
    void record(String type, String content);

    PageVO<OperationLog> page(Integer page, Integer pageSize);
}
