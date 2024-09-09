package org.example.yobiapi.content_manual.Controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.content_manual.Entity.ContentType;
import org.example.yobiapi.content_manual.Entity.Content_Manual;
import org.example.yobiapi.content_manual.Service.Content_ManualService;
import org.example.yobiapi.content_manual.dto.Content_ManualDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Content_ManualController {
    private final Content_ManualService contentManualService;

    // 게시글 사진 추가 또는 레시피 사진과 설명 추가
    @PostMapping("/add")
    public ResponseEntity<Content_ManualDTO> addContentManual(
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") Content_ManualDTO dto) {
        try {
            Content_ManualDTO createdManual = contentManualService.addContentManual(dto, file);
            return ResponseEntity.ok(createdManual);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Content_Manual 수정
    @PutMapping("/update/{manualId}")
    public ResponseEntity<Content_ManualDTO> updateContentManual(
            @PathVariable Integer manualId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("data") Content_ManualDTO dto) {
        try {
            Content_ManualDTO updatedManual = contentManualService.updateContentManual(manualId, dto, file);
            return ResponseEntity.ok(updatedManual);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Content_Manual 삭제
    @DeleteMapping("/delete/{manualId}")
    public ResponseEntity<Void> deleteContentManual(@PathVariable Integer manualId) {
        try {
            contentManualService.deleteContentManual(manualId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 contentType과 contentId로 Content_Manual 조회
    @GetMapping("/{contentType}/{contentId}")
    public ResponseEntity<List<Content_ManualDTO>> getContentManuals(
            @PathVariable("contentType") ContentType contentType,
            @PathVariable("contentId") Integer contentId) {
        List<Content_ManualDTO> manuals = contentManualService.getContentManuals(contentType, contentId);
        return ResponseEntity.ok(manuals);
    }
}
