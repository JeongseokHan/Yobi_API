package org.example.yobiapi.recipe.service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.content_manual.Entity.ContentType;
import org.example.yobiapi.content_manual.Entity.Content_Manual;
import org.example.yobiapi.content_manual.Entity.Content_ManualRepository;
import org.example.yobiapi.content_manual.dto.Content_ManualDTO;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.recipe.Entity.RecipeProjection;
import org.example.yobiapi.recipe.Entity.RecipeRepository;
import org.example.yobiapi.recipe.dto.*;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    String key = "29cf6df4f3f84cbf82be";
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final Content_ManualRepository contentManualRepository;

    public Integer saveRecipe(RecipeDTO recipeDTO) {
        User user = userRepository.findByUserId(recipeDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            if(recipeDTO.getCategory().length() > 45) {
                throw new CustomException(CustomErrorCode.CATEGORY_LONG_REQUEST);
            }
            else if(recipeDTO.getCategory().isEmpty()) {
                throw new CustomException(CustomErrorCode.CATEGORY_IS_EMPTY);
            }
            else if(recipeDTO.getTitle().length() > 45) {
                throw new CustomException(CustomErrorCode.Title_LONG_REQUEST);
            }
            else if(recipeDTO.getTitle().isEmpty()) {
                throw new CustomException(CustomErrorCode.Title_IS_EMPTY);
            }
            else if(recipeDTO.getIngredient().length() > 300) {
                throw new CustomException(CustomErrorCode.INGREDIENT_LONG_REQUEST);
            }
            else if(recipeDTO.getIngredient().isEmpty()) {
                throw new CustomException(CustomErrorCode.INGREDIENT_IS_EMPTY);
            }
            else {
                Recipe recipe = Recipe.toRecipe(recipeDTO, user);
                recipeRepository.save(recipe);
                return recipe.getRecipeId();
            }
        }
    }

    public HttpStatus updateRecipe(Integer recipeId,UpdateRecipeDTO updateRecipeDTO) {
        Recipe updatedRecipe = recipeRepository.findByRecipeId(recipeId);
        User user = userRepository.findByUserId(updateRecipeDTO.getUserId());
        if(updatedRecipe == null) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        else {
            if(user == null) {
                throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
            }
            else {
                if(!Objects.equals(updatedRecipe.getUser().getUserId(), updateRecipeDTO.getUserId())) {
                    throw new CustomException(CustomErrorCode.AUTHOR_NOT_EQUAL);
                }
                else if(updateRecipeDTO.getCategory().length() > 45) {
                    throw new CustomException(CustomErrorCode.CATEGORY_LONG_REQUEST);
                }
                else if(updateRecipeDTO.getCategory().isEmpty()) {
                    throw new CustomException(CustomErrorCode.CATEGORY_IS_EMPTY);
                }
                else if(updateRecipeDTO.getTitle().length() > 45) {
                    throw new CustomException(CustomErrorCode.Title_LONG_REQUEST);
                }
                else if(updateRecipeDTO.getTitle().isEmpty()) {
                    throw new CustomException(CustomErrorCode.Title_IS_EMPTY);
                }
                else if(updateRecipeDTO.getIngredient().length() > 300) {
                    throw new CustomException(CustomErrorCode.INGREDIENT_LONG_REQUEST);
                }
                else if(updatedRecipe.getIngredient().isEmpty()) {
                    throw new CustomException(CustomErrorCode.INGREDIENT_IS_EMPTY);
                }
                else {
                    updatedRecipe.setCategory(updateRecipeDTO.getCategory());
                    updatedRecipe.setTitle(updateRecipeDTO.getTitle());
                    updatedRecipe.setIngredient(updateRecipeDTO.getIngredient());
                    updatedRecipe.setUpdateDate(LocalDateTime.now());
                    updatedRecipe.setRecipeThumbnail(updateRecipeDTO.getRecipeThumbnail());
                    recipeRepository.save(updatedRecipe);
                    return HttpStatus.OK;
                }
            }
        }
    }

    public HttpStatus deleteRecipe(Integer recipeId, DeleteRecipeDTO deleteRecipeDTO) {
        Recipe recipe = recipeRepository.findByRecipeId(recipeId);
        if (recipe == null) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        } else {
            if (!Objects.equals(recipe.getUser().getUserId(), deleteRecipeDTO.getUserId())) {
                throw new CustomException(CustomErrorCode.AUTHOR_NOT_EQUAL);
            } else {
                recipeRepository.delete(recipe);
                return HttpStatus.OK;
            }
        }
    }

    public Page<RecipeProjection> SearchRecipe_Title(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecipeProjection> recipes = recipeRepository.findAllByTitleContaining(title, pageable);
        if(recipes.isEmpty()) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        else {
            return recipes;
        }
    }

    public Page<RecipeProjection> SearchRecipe_Category(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecipeProjection> recipes = recipeRepository.findAllByCategoryContaining(category, pageable);
        if(recipes.isEmpty()) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        else {
            return recipes;
        }
    }

    public Page<RecipeProjection> SearchRecipe_User(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            Page<RecipeProjection> recipes = recipeRepository.findAllByUser(user, pageable);
            if(recipes.isEmpty()) {
                throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
            }
            else {
                return recipes;
            }
        }
    }

    public Page<RecipeProjection> SearchRecipe_All(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecipeProjection> recipePage = recipeRepository.findAllBy(pageable);
        if(recipePage.isEmpty()) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        return recipePage;
    }

    public HttpStatus fetchDataAndPrint() {
        try {
            String apiURL = "http://openapi.foodsafetykorea.go.kr/api/" +
                    key + "/COOKRCP01/json/1/999";
            URL url = new URL(apiURL);
            User user = userRepository.findByUserId("seoil12345");

            // 한글 인코딩 추가
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            // JSON 데이터 구조에 따라 필요한 정보를 추출하여 출력합니다.
            JSONObject cookRcp01 = (JSONObject) jsonObject.get("COOKRCP01");
            JSONArray recipeArray = (JSONArray) cookRcp01.get("row");

            List<Recipe> recipeList = new ArrayList<>();
            for (Object obj : recipeArray) {
                JSONObject recipe = (JSONObject) obj;
                Recipe defRecipe = new Recipe();

                // RCP_PARTS_DTLS에서 정규식을 이용하여 재료 목록 추출
                String rcpPartsDtls = recipe.get("RCP_PARTS_DTLS").toString();
                String ingredients = extractIngredients(rcpPartsDtls);

                // 특정 구분자 '●'가 없고 재료 목록이 유효한 경우에만 추가
                if (!rcpPartsDtls.contains("●") && !ingredients.isEmpty() && !rcpPartsDtls.contains(":")
                        && !rcpPartsDtls.contains("소스") && !rcpPartsDtls.contains("재료") && rcpPartsDtls.length() > 5
                        && !rcpPartsDtls.contains("[")) {
                    defRecipe.setTitle(recipe.get("RCP_NM").toString());
                    defRecipe.setIngredient(ingredients);  // 추출된 재료 목록만 설정
                    defRecipe.setRecipeThumbnail(recipe.get("ATT_FILE_NO_MAIN").toString());
                    defRecipe.setCategory("요비");
                    defRecipe.setUser(user);
                    recipeList.add(defRecipe);
                }
            }
            recipeRepository.saveAll(recipeList);
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

    public RecipeDTO getRecipeWithManuals(Integer recipeId) {
        // 레시피 조회
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.Recipe_NOT_FOUND));

        // 레시피에 연결된 Content_Manual 조회
        List<Content_Manual> manuals = contentManualRepository.findByContentTypeAndContentId(ContentType.RECIPE, recipeId);

        // Content_Manual 데이터를 ContentManualDTO로 변환
        List<Content_ManualDTO> manualDTOs = manuals.stream()
                .map(manual -> {
                    Content_ManualDTO manualDTO = new Content_ManualDTO();
                    manualDTO.setManualId(manual.getManualId());
                    manualDTO.setStepNumber(manual.getStepNumber());
                    manualDTO.setDescription(manual.getDescription());
                    manualDTO.setImage(manual.getImage());
                    return manualDTO;
                }).collect(Collectors.toList());

        // Recipe 데이터를 RecipeDTO로 변환
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setRecipeId(recipe.getRecipeId());
        recipeDTO.setTitle(recipe.getTitle());
        recipeDTO.setCategory(recipe.getCategory());
        recipeDTO.setIngredient(recipe.getIngredient());
        recipeDTO.setViews(recipe.getViews());
        recipeDTO.setReportCount(recipe.getReportCount());
        recipeDTO.setCreateDate(recipe.getCreateDate());
        recipeDTO.setUpdateDate(recipe.getUpdateDate());
        recipeDTO.setRecipeThumbnail(recipe.getRecipeThumbnail());
        recipeDTO.setManuals(manualDTOs);

        return recipeDTO;
    }

}
