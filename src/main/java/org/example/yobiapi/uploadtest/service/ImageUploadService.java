package org.example.yobiapi.uploadtest.service;

import java.util.logging.Logger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService {

    private static final Logger logger = Logger.getLogger(ImageUploadService.class.getName());

    private final AmazonS3 s3Client;

    @Autowired
    public ImageUploadService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Value("${s3.bucket}")
    private String bucket;

    public String upload(MultipartFile image) throws IOException {
        try {
            /* 업로드할 파일의 이름을 변경 */
            String originalFileName = image.getOriginalFilename();
            String fileName = changeFileName(originalFileName);

            /* 로그 추가: 파일 이름과 경로 출력 */
            logger.info("Uploading file to S3 with name: " + fileName);

            /* S3에 업로드할 파일의 메타데이터 생성 */
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(image.getContentType());
            metadata.setContentLength(image.getSize());

            /* S3에 파일 업로드 */
            s3Client.putObject(bucket, fileName, image.getInputStream(), metadata);

            /* 업로드한 파일의 S3 URL 주소 반환 */
            String fileUrl = s3Client.getUrl(bucket, fileName).toString();
            logger.info("File uploaded to S3 with URL: " + fileUrl);

            return fileUrl;
        } catch (AmazonServiceException e) {
            // S3 관련 에러 처리
            e.printStackTrace();
            throw new IOException("S3에서 파일 업로드 중 오류가 발생했습니다.");
        } catch (SdkClientException e) {
            // S3 클라이언트 관련 에러 처리
            e.printStackTrace();
            throw new IOException("S3 클라이언트와의 통신 중 오류가 발생했습니다.");
        }
    }

    private String changeFileName(String originalFileName) {
        /* 업로드할 파일의 이름을 변경하는 로직 */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return originalFileName + "_" + LocalDateTime.now().format(formatter);
    }
}
