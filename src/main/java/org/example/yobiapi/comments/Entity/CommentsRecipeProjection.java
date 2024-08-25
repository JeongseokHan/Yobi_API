package org.example.yobiapi.comments.Entity;

import org.example.yobiapi.recipe.Entity.RecipeIdProjection;
import org.example.yobiapi.user.Entity.UserProjection;

import java.time.LocalDateTime;

public interface CommentsRecipeProjection {
    Integer getCommentId();
    RecipeIdProjection getRecipe();
    UserProjection getUser();
    String getContent();
    LocalDateTime getCreateContent();
    LocalDateTime getUpdateContent();
    Integer getReportCount();
    Integer getParentCommentId();
}
