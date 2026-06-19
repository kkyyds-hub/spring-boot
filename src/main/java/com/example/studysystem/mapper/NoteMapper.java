package com.example.studysystem.mapper;

import com.example.studysystem.entity.MaterialNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoteMapper {
    int insertBatch(@Param("notes") List<MaterialNote> notes);

    int deleteByMaterialId(@Param("materialId") Long materialId);

    int deleteByMaterialIds(@Param("ids") List<Long> ids);

    List<MaterialNote> selectByMaterialId(@Param("materialId") Long materialId);
}
