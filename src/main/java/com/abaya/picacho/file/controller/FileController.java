package com.abaya.picacho.file.controller;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.file.service.FileStorageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Slf4j
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UploadFilePayload {
        private String nickName;
        private String fileName;
    }

    @CrossOrigin
    @GetMapping("/file/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws ServiceException {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = getContentType(request.getServletContext(), resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String getContentType(ServletContext servletContext, Resource resource) throws ServiceException {
        try {
            String contentType = servletContext.getMimeType(resource.getFile().getAbsolutePath());
            if (contentType != null) return contentType;

            return "application/octet-stream";
        } catch (IOException ex) {
            throw new ServiceException("系统内部处理错误！", ex);
        }
    }
}