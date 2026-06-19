package com.example.studysystem.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.example.studysystem.service.CloudFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class CloudFileServiceImpl implements CloudFileService {
    @Value("${app.upload-dir}")
    private String uploadDir;

    @Value("${aliyun.oss.endpoint:}")
    private String endpoint;

    @Value("${aliyun.oss.bucket:}")
    private String bucket;

    @Value("${aliyun.oss.access-key-id:}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret:}")
    private String accessKeySecret;

    @Override
    public UploadResult upload(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
        String suffix = originalName.contains(".") ? originalName.substring(originalName.lastIndexOf(".")) : "";
        String fileName = UUID.randomUUID() + suffix;
        if (hasOssConfig()) {
            return uploadToOss(file, fileName);
        }
        return uploadToLocal(file, fileName);
    }

    private boolean hasOssConfig() {
        return StringUtils.hasText(endpoint)
                && StringUtils.hasText(bucket)
                && StringUtils.hasText(accessKeyId)
                && StringUtils.hasText(accessKeySecret);
    }

    private UploadResult uploadToOss(MultipartFile file, String fileName) throws IOException {
        String day = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String objectName = "uploads/" + day + "/" + fileName;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            ossClient.putObject(bucket, objectName, file.getInputStream(), metadata);
        } finally {
            ossClient.shutdown();
        }
        String url = endpoint.replace("https://", "https://" + bucket + ".")
                .replace("http://", "http://" + bucket + ".") + "/" + objectName;
        return new UploadResult(objectName, url);
    }

    private UploadResult uploadToLocal(MultipartFile file, String fileName) throws IOException {
        Path dir = Path.of(uploadDir);
        Files.createDirectories(dir);
        file.transferTo(dir.resolve(fileName));
        return new UploadResult(fileName, "/uploads/" + fileName);
    }
}
