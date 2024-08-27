package org.example.yobiapi.report.Entity;

import org.example.yobiapi.comments.Entity.CommentsIdProjection;
import org.example.yobiapi.recipe.Entity.RecipeIdProjection;
import org.example.yobiapi.user.Entity.UserProjection;

import java.time.LocalDateTime;

public interface ReportCommentProjection {
    Integer getReportId();
    UserProjection getUser();
    CommentsIdProjection getComments();
    String getReason();
    Integer getResolved();
    LocalDateTime getResolvedDate();
}
