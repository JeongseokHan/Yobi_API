package org.example.yobiapi.report.Entity;

import org.example.yobiapi.board.Entity.BoardIdProjection;
import org.example.yobiapi.comments.Entity.CommentsIdProjection;
import org.example.yobiapi.recipe.Entity.RecipeIdProjection;
import org.example.yobiapi.user.Entity.UserProjection;

import java.time.LocalDateTime;

public interface ReportAllProjection {
    Integer getReportId();
    UserProjection getUser();
    String getReason();
    Integer getResolved();
    LocalDateTime getResolvedDate();
    BoardIdProjection getBoard();
    RecipeIdProjection getRecipe();
    CommentsIdProjection getComments();
}
