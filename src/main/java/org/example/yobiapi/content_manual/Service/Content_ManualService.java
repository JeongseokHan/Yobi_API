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
import org.example.yobiapi.user.Entity.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Content_ManualService {

    private final Content_ManualRepository contentManualRepository;
    private final AmazonS3 s3Client;

    private String bucketName = "yobibucket";


    public HttpStatus fetchDataAndPrint() {
        try {
            String apiURL = "http://openapi.foodsafetykorea.go.kr/api/" + key + "/COOKRCP01/json/1/999";
            URL url = new URL(apiURL);

            // 한글 인코딩 추가
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            // JSON 데이터 구조에 따라 필요한 정보를 추출합니다.
            JSONObject cookRcp01 = (JSONObject) jsonObject.get("COOKRCP01");
            JSONArray recipeArray = (JSONArray) cookRcp01.get("row");

            List<Content_Manual> contentManuals = new ArrayList<>();
            for (Object obj : recipeArray) {
                JSONObject recipe = (JSONObject) obj;

                // RCP_PARTS_DTLS에서 정규식을 이용하여 재료 목록 추출
                String rcpPartsDtls = recipe.get("RCP_PARTS_DTLS").toString();
                String ingredients = extractIngredients(rcpPartsDtls);

                // 레시피가 가져온 데이터와 일치시키기 위해 동일한 방식 사용
                if (!rcpPartsDtls.contains("●") && !ingredients.isEmpty() && !rcpPartsDtls.contains(":")
                        && !rcpPartsDtls.contains("소스") && !rcpPartsDtls.contains("재료") && rcpPartsDtls.length() > 5
                        && !rcpPartsDtls.contains("[")) {

                    // StepNumber에 해당하는 MANUAL 및 MANUAL_IMG를 1~20까지 처리
                    for (int i = 1; i <= 20; i++) {
                        String manualKey = String.format("MANUAL%02d", i);
                        String manualImgKey = String.format("MANUAL_IMG%02d", i);

                        // MANUAL 및 MANUAL_IMG 데이터가 있는지 확인
                        if (recipe.containsKey(manualKey) && recipe.containsKey(manualImgKey)) {
                            String manual = recipe.get(manualKey).toString();
                            String manualImg = recipe.get(manualImgKey).toString();

                            // MANUAL 내용이 비어있지 않으면 추가
                            if (!manual.trim().isEmpty()) {
                                Content_Manual contentManual = new Content_Manual();
                                contentManual.setContentId(Integer.parseInt(recipe.get("RCP_SEQ").toString()));
                                contentManual.setContentType(ContentType.RECIPE);
                                contentManual.setDescription(manual);
                                contentManual.setImage(manualImg);
                                contentManual.setStepNumber(i);
                                contentManuals.add(contentManual);
                            }
                        }
                    }
                }
            }

            // 저장
            contentManualRepository.saveAll(contentManuals);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpStatus.OK;
    }

    private String extractIngredients(String rcpPartsDtls) {
        String[] lines = rcpPartsDtls.split("\\r?\\n");
        if (lines.length > 0) {
            String firstLine = lines[0].trim();
            if (firstLine.length() <= 15 && lines.length > 1) {
                return lines[1].trim();
            } else {
                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    sb.append(line.trim()).append(" ");
                }
                return sb.toString().trim();
            }
        }
        return rcpPartsDtls.trim();
    }

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
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
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
