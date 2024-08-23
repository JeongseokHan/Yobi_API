package org.example.yobiapi.recipe.Entity;

import org.example.yobiapi.user.Entity.UserProjection;

import java.time.LocalDateTime;

public interface RecipeProjection {
    Integer getRecipeId();
    UserProjection getUser();
    String getTitle();
    String getCategory();
    String getIngredient();
    Integer getViews();
    Integer getReportCount();
    LocalDateTime getCreateDate();
    LocalDateTime getUpdateDate();
    String getRecipeThumbnail();
}
