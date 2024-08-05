package org.example.yobiapi.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeReportDTO {
    private Integer recipeId;
    private String userId;
    private String reason;
}
