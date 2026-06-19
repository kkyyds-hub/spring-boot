package com.example.studysystem.mapper;

import com.example.studysystem.entity.MaterialCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int insert(MaterialCategory category);

    int update(MaterialCategory category);

    int deleteById(@Param("id") Long id);

    List<MaterialCategory> list();
}
