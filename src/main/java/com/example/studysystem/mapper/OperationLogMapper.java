package com.example.studysystem.mapper;

import com.example.studysystem.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    int insert(OperationLog log);

    List<OperationLog> page(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    long count();
}
