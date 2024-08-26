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

    public static Report BoardReport(Board board, User user, BoardReportDTO boardReportDTO) {
        Report report = new Report();
        report.setBoard(board);
        report.setUser(user);
        report.setReason(boardReportDTO.getReason());

        return report;
    }

    public static Report RecipeReport(Recipe recipe, User user, RecipeReportDTO recipeReportDTO) {
        Report report = new Report();
        report.setRecipe(recipe);
        report.setUser(user);
        report.setReason(recipeReportDTO.getReason());

        return report;
    }

    public static Report CommentReport(Comments comments, User user, CommentReportDTO commentReportDTO) {
        Report report = new Report();
        report.setComments(comments);
        report.setUser(user);
        report.setReason(commentReportDTO.getReason());

        return report;
    }
}
