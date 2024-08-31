package org.example.yobiapi.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.content_manual.dto.Content_ManualDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private String userId;
    private Integer recipeId;
    private String title;
    private String category;
    private String ingredient;
    private Integer views;
    private Integer reportCount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String recipeThumbnail;
    private List<Content_ManualDTO> manuals;
}
