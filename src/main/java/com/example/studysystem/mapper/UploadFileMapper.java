package com.example.studysystem.mapper;

import com.example.studysystem.entity.UploadFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UploadFileMapper {
    int insert(UploadFile uploadFile);

    List<UploadFile> selectByMaterialId(@Param("materialId") Long materialId);

    int deleteById(@Param("id") Long id);
}
