package org.example.yobiapi.content_manual.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.example.yobiapi.content_manual.Entity.ContentType;
import org.example.yobiapi.content_manual.Entity.Content_Manual;
import org.example.yobiapi.content_manual.Entity.Content_ManualRepository;
import org.example.yobiapi.content_manual.dto.Content_ManualDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Content_ManualService {

    private final Content_ManualRepository contentManualRepository;
    private final AmazonS3 s3Client;

    private String bucketName = "yobibucket";

    // 게시글 사진 추가 또는 레시피 사진과 설명 추가
    public Content_ManualDTO addContentManual(Content_ManualDTO dto, MultipartFile file) {
        String fileUrl = uploadFileToS3(file, dto.getContentType());
        dto.setImage(fileUrl);
        Content_Manual contentManual = Content_Manual.toContent_Manual(dto);
        contentManual = contentManualRepository.save(contentManual);
        return new Content_ManualDTO(
                contentManual.getManualId(),
                contentManual.getContentType(),
                contentManual.getContentId(),
                contentManual.getStepNumber(),
                contentManual.getDescription(),
                contentManual.getImage()
        );
    }

    // S3에 파일 업로드
    private String uploadFileToS3(MultipartFile file, ContentType contentType) {
        String folderName = contentType == ContentType.BOARD ? "board" : "recipe";
        String fileName = folderName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            File tempFile = File.createTempFile("temp", null);  // 임시 파일 생성
            file.transferTo(tempFile);  // MultipartFile을 임시 파일로 변환

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, tempFile);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(putObjectRequest);
            tempFile.delete();  // 업로드 후 임시 파일 삭제

        } catch (IOException e) {
            throw new RuntimeException("s3 업로드 실패");
        }

        return s3Client.getUrl(bucketName, fileName).toString();
    }

    // S3에서 파일 삭제
    private void deleteFileFromS3(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.indexOf(".com/") + 5);  // 파일 URL에서 파일 경로 추출
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (Exception e) {
            throw new RuntimeException("s3 삭제 실패");
        }
    }

    // Content_Manual 수정
    public Content_ManualDTO updateContentManual(Integer manualId, Content_ManualDTO dto, MultipartFile file) {
        Content_Manual contentManual = contentManualRepository.findById(manualId)
                .orElseThrow(() -> new RuntimeException("메뉴얼을 찾을 수 없습니다."));

        if (file != null && !file.isEmpty()) {
            // 기존 이미지 삭제
            if (contentManual.getImage() != null) {
                deleteFileFromS3(contentManual.getImage());
            }
            // 새로운 이미지 업로드
            String fileUrl = uploadFileToS3(file, dto.getContentType());
            dto.setImage(fileUrl);
        }

        contentManual.setContentType(dto.getContentType());
        contentManual.setContentId(dto.getContentId());
        contentManual.setStepNumber(dto.getStepNumber());
        contentManual.setDescription(dto.getDescription());
        contentManual.setImage(dto.getImage());

        contentManual = contentManualRepository.save(contentManual);
        return new Content_ManualDTO(
                contentManual.getManualId(),
                contentManual.getContentType(),
                contentManual.getContentId(),
                contentManual.getStepNumber(),
                contentManual.getDescription(),
                contentManual.getImage()
        );
    }

    // Content_Manual 삭제
    public void deleteContentManual(Integer manualId) {
        Content_Manual contentManual = contentManualRepository.findById(manualId)
                .orElseThrow(() -> new RuntimeException("메뉴얼을 찾을 수 없습니다."));

        // 이미지 삭제
        if (contentManual.getImage() != null) {
            deleteFileFromS3(contentManual.getImage());
        }

        contentManualRepository.delete(contentManual);
    }

    // 특정 contentType과 contentId로 Content_Manual 조회
    public List<Content_ManualDTO> getContentManuals(ContentType contentType, Integer contentId) {
        List<Content_Manual> manuals = contentManualRepository.findByContentTypeAndContentId(contentType, contentId);
        return manuals.stream()
                .map(manual -> new Content_ManualDTO(
                        manual.getManualId(),
                        manual.getContentType(),
                        manual.getContentId(),
                        manual.getStepNumber(),
                        manual.getDescription(),
                        manual.getImage()
                )).collect(Collectors.toList());
    }
}
