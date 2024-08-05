package org.example.yobiapi.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCommentsDTO {
    private Integer recipeId;
    private String userId;
    private String content;
    private Integer contentId;
}
