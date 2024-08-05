package org.example.yobiapi.report.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.comments.Entity.Comments;
import org.example.yobiapi.contenttypes.Entity.ContentTypes;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.report.dto.BoardReportDTO;
import org.example.yobiapi.report.dto.CommentReportDTO;
import org.example.yobiapi.report.dto.RecipeReportDTO;
import org.example.yobiapi.user.Entity.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "report_id")
    private Integer reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", referencedColumnName = "content_id")
    private ContentTypes contentTypes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private Comments comments;

    @Column(nullable = false, name = "reason")
    private String reason;

    @Column(name = "resolved")
    private Integer resolved;

    @CreationTimestamp
    @Column(name = "resolved_date")
    private LocalDateTime resolvedDate;

    public static Report BoardReport(BoardReportDTO boardReportDTO) {
        Report report = new Report();
        report.board.setBoardId(boardReportDTO.getBoardId());
        report.user.setUserId(boardReportDTO.getUserId());
        report.setReason(boardReportDTO.getReason());

        return report;
    }

    public static Report RecipeReport(RecipeReportDTO recipeReportDTO) {
        Report report = new Report();
        report.recipe.setRecipeId(recipeReportDTO.getRecipeId());
        report.user.setUserId(recipeReportDTO.getUserId());
        report.setReason(recipeReportDTO.getReason());

        return report;
    }

    public static Report CommentReport(CommentReportDTO commentReportDTO) {
        Report report = new Report();
        report.comments.setCommentId(commentReportDTO.getCommentId());
        report.contentTypes.setContentId(commentReportDTO.getContentId());
        report.user.setUserId(commentReportDTO.getUserId());
        report.setReason(commentReportDTO.getReason());

        return report;
    }
}
