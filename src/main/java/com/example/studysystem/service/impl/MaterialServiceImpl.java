package com.example.studysystem.service.impl;

import com.example.studysystem.dto.MaterialDTO;
import com.example.studysystem.dto.NoteDTO;
import com.example.studysystem.entity.MaterialNote;
import com.example.studysystem.entity.StudyMaterial;
import com.example.studysystem.mapper.MaterialMapper;
import com.example.studysystem.mapper.NoteMapper;
import com.example.studysystem.mapper.UploadFileMapper;
import com.example.studysystem.service.LogService;
import com.example.studysystem.service.MaterialService;
import com.example.studysystem.utils.UserContext;
import com.example.studysystem.vo.MaterialDetailVO;
import com.example.studysystem.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    private final MaterialMapper materialMapper;
    private final NoteMapper noteMapper;
    private final UploadFileMapper uploadFileMapper;
    private final LogService logService;

    public MaterialServiceImpl(MaterialMapper materialMapper, NoteMapper noteMapper, UploadFileMapper uploadFileMapper, LogService logService) {
        this.materialMapper = materialMapper;
        this.noteMapper = noteMapper;
        this.uploadFileMapper = uploadFileMapper;
        this.logService = logService;
    }

    @Override
    @Transactional
    public Long add(MaterialDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        StudyMaterial material = new StudyMaterial();
        material.setUserId(UserContext.getUserId());
        material.setTitle(dto.getTitle());
        material.setCategory(dto.getCategory());
        material.setStatus(defaultStatus(dto.getStatus()));
        material.setImportant(Boolean.TRUE.equals(dto.getImportant()));
        material.setContent(dto.getContent());
        material.setCreateTime(now);
        material.setUpdateTime(now);
        materialMapper.insert(material);
        saveNotes(material.getId(), dto.getNotes(), now);
        logService.record("新增资料", "新增学习资料：" + material.getTitle());
        return material.getId();
    }

    @Override
    @Transactional
    public void update(Long id, MaterialDTO dto) {
        if (materialMapper.selectById(id) == null) {
            throw new RuntimeException("资料不存在");
        }
        StudyMaterial material = new StudyMaterial();
        material.setId(id);
        material.setUserId(UserContext.getUserId());
        material.setTitle(dto.getTitle());
        material.setCategory(dto.getCategory());
        material.setStatus(defaultStatus(dto.getStatus()));
        material.setImportant(Boolean.TRUE.equals(dto.getImportant()));
        material.setContent(dto.getContent());
        material.setUpdateTime(LocalDateTime.now());
        materialMapper.update(material);
        noteMapper.deleteByMaterialId(id);
        saveNotes(id, dto.getNotes(), LocalDateTime.now());
        logService.record("修改资料", "修改学习资料：" + dto.getTitle());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        StudyMaterial material = materialMapper.selectById(id);
        if (material == null) {
            throw new RuntimeException("资料不存在");
        }
        noteMapper.deleteByMaterialId(id);
        materialMapper.deleteById(id);
        logService.record("删除资料", "删除学习资料：" + (material == null ? id : material.getTitle()));
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        noteMapper.deleteByMaterialIds(ids);
        materialMapper.deleteBatch(ids);
        logService.record("批量删除", "批量删除学习资料：" + ids);
    }

    @Override
    public MaterialDetailVO detail(Long id) {
        StudyMaterial material = materialMapper.selectById(id);
        if (material == null) {
            throw new RuntimeException("资料不存在");
        }
        MaterialDetailVO vo = new MaterialDetailVO();
        BeanUtils.copyProperties(material, vo);
        vo.setNotes(noteMapper.selectByMaterialId(id));
        vo.setFiles(uploadFileMapper.selectByMaterialId(id));
        return vo;
    }

    @Override
    public PageVO<StudyMaterial> page(Integer page, Integer pageSize, String title, String category, String status, Boolean important, String startDate, String endDate) {
        int offset = (page - 1) * pageSize;
        List<StudyMaterial> records = materialMapper.page(title, category, status, important, startDate, endDate, offset, pageSize);
        long total = materialMapper.count(title, category, status, important, startDate, endDate);
        return new PageVO<>(total, records);
    }

    private String defaultStatus(String status) {
        return status == null || status.isBlank() ? "学习中" : status;
    }

    private void saveNotes(Long materialId, List<NoteDTO> noteDTOList, LocalDateTime now) {
        if (noteDTOList == null || noteDTOList.isEmpty()) {
            return;
        }
        List<MaterialNote> notes = new ArrayList<>();
        for (NoteDTO dto : noteDTOList) {
            MaterialNote note = new MaterialNote();
            note.setMaterialId(materialId);
            note.setCreateUser(UserContext.get());
            note.setNoteTitle(dto.getNoteTitle());
            note.setNoteContent(dto.getNoteContent());
            note.setCreateTime(now);
            note.setUpdateTime(now);
            notes.add(note);
        }
        noteMapper.insertBatch(notes);
    }
}
