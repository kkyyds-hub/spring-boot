package com.example.studysystem.mapper;

import com.example.studysystem.entity.StudyMaterial;
import com.example.studysystem.vo.NameValueVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaterialMapper {
    int insert(StudyMaterial material);

    int update(StudyMaterial material);

    int deleteById(@Param("id") Long id);

    int deleteBatch(@Param("ids") List<Long> ids);

    StudyMaterial selectById(@Param("id") Long id);

    List<StudyMaterial> page(@Param("title") String title,
                             @Param("category") String category,
                             @Param("status") String status,
                             @Param("important") Boolean important,
                             @Param("startDate") String startDate,
                             @Param("endDate") String endDate,
                             @Param("offset") Integer offset,
                             @Param("pageSize") Integer pageSize);

    long count(@Param("title") String title,
               @Param("category") String category,
               @Param("status") String status,
               @Param("important") Boolean important,
               @Param("startDate") String startDate,
               @Param("endDate") String endDate);

    List<NameValueVO> countByCategory();

    List<NameValueVO> countByMonth();

    List<NameValueVO> countByStatus();
}
