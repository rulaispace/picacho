package com.abaya.picacho.biz.file.service.impl;

import com.abaya.picacho.common.config.FileStorageProperties;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.RandomUtils;
import com.abaya.picacho.biz.file.model.FileIndicator;
import com.abaya.picacho.biz.file.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) throws IOException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }

    public FileIndicator storeFile(MultipartFile file, String operator) throws ServiceException {
        Assert.notNull(file, "待存储文件不能为空");
        Assert.notNull(operator, "操作人员不能为空");
        return new FileIndicator(file.getOriginalFilename(), storeFileInLocalStorage(file));
    }

    private String storeFileInLocalStorage(MultipartFile file) throws ServiceException {
        try {
            String fileName = createFileName(file);
            if(fileName.contains("..")) throw new ServiceException("文件名异常，无法存储！");

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new ServiceException("无法存储文件", ex);
        }
    }

    private String createFileName(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String postfix = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
        return RandomUtils.uuid() + postfix;
    }

    public Resource loadFileAsResource(String fileName) throws ServiceException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ServiceException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new ServiceException("File not found " + fileName, ex);
        }
    }

    public boolean deleteFileInLocalStorage(String fileName) throws ServiceException {
        try {
            return Files.deleteIfExists(this.fileStorageLocation.resolve(fileName));
        } catch (IOException e) {
            throw new ServiceException("删除文件(%s)失败", fileName);
        }
    }
}