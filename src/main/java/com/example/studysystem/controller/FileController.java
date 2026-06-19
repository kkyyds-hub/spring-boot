package com.example.studysystem.controller;

import com.example.studysystem.entity.UploadFile;
import com.example.studysystem.service.FileService;
import com.example.studysystem.vo.ApiResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // 公共资料库列表
    @GetMapping
    public ApiResult<List<UploadFile>> list() {
        return ApiResult.success(fileService.listPublicFiles());
    }

    // 文件上传
    @PostMapping("/upload")
    public ApiResult<UploadFile> upload(@RequestParam("file") MultipartFile file, Long materialId) throws Exception {
        return ApiResult.success(fileService.upload(file, materialId));
    }

    // 删除附件记录
    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable Long id) {
        fileService.delete(id);
        return ApiResult.success(null);
    }
}
