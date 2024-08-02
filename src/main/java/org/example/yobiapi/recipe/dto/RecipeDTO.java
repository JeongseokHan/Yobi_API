package org.example.yobiapi.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private String userId;
    private String title;
    private String category;
    private String ingredient;
}
