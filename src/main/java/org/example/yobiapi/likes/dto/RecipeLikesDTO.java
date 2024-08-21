package org.example.yobiapi.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeLikesDTO {
    private String userId;
    private Integer recipeId;
}
