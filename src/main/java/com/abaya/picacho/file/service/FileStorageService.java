package com.abaya.picacho.file.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.file.model.FileIndicator;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileIndicator storeFile(MultipartFile file, String operator) throws ServiceException;

    Resource loadFileAsResource(String fileName) throws ServiceException;

    boolean deleteFileInLocalStorage(String fileName) throws ServiceException;
}
