package org.example.yobiapi.uploadtest.controller;

import java.io.IOException;
import org.example.yobiapi.uploadtest.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/upload")
    public String upload(MultipartFile image) throws IOException {
        // 이미지 업로드 로직
        return imageUploadService.upload(image);
    }
}
