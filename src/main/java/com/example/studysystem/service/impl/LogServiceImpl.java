package com.example.studysystem.service.impl;

import com.example.studysystem.entity.OperationLog;
import com.example.studysystem.mapper.OperationLogMapper;
import com.example.studysystem.service.LogService;
import com.example.studysystem.utils.UserContext;
import com.example.studysystem.vo.PageVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    private final OperationLogMapper logMapper;

    public LogServiceImpl(OperationLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public void record(String type, String content) {
        OperationLog log = new OperationLog();
        log.setUserId(UserContext.getUserId());
        log.setOperator(UserContext.get());
        log.setOperationType(type);
        log.setOperationContent(content);
        log.setOperationTime(LocalDateTime.now());
        logMapper.insert(log);
    }

    @Override
    public PageVO<OperationLog> page(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        List<OperationLog> records = logMapper.page(offset, pageSize);
        return new PageVO<>(logMapper.count(), records);
    }
}
