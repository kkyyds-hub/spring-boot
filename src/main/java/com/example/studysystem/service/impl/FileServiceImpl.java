package com.example.studysystem.service.impl;

import com.example.studysystem.entity.UploadFile;
import com.example.studysystem.mapper.UploadFileMapper;
import com.example.studysystem.service.CloudFileService;
import com.example.studysystem.service.FileService;
import com.example.studysystem.service.LogService;
import com.example.studysystem.utils.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FileServiceImpl implements FileService {
    private final CloudFileService cloudFileService;
    private final UploadFileMapper uploadFileMapper;
    private final LogService logService;

    public FileServiceImpl(CloudFileService cloudFileService, UploadFileMapper uploadFileMapper, LogService logService) {
        this.cloudFileService = cloudFileService;
        this.uploadFileMapper = uploadFileMapper;
        this.logService = logService;
    }

    @Override
    public UploadFile upload(MultipartFile file, Long materialId) throws IOException {
        CloudFileService.UploadResult result = cloudFileService.upload(file);
        UploadFile uploadFile = new UploadFile();
        uploadFile.setUserId(UserContext.getUserId());
        uploadFile.setMaterialId(materialId);
        uploadFile.setOriginalName(file.getOriginalFilename());
        uploadFile.setFileName(result.fileName());
        uploadFile.setFileUrl(result.fileUrl());
        uploadFile.setFileSize(file.getSize());
        uploadFile.setUploadUser(UserContext.get());
        uploadFile.setCreateTime(LocalDateTime.now());
        uploadFileMapper.insert(uploadFile);
        logService.record("文件上传", "上传文件：" + file.getOriginalFilename());
        return uploadFile;
    }

    @Override
    public void delete(Long id) {
        uploadFileMapper.deleteById(id);
        logService.record("删除附件", "删除附件记录：" + id);
    }
}
