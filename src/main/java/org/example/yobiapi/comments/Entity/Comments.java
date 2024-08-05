package org.example.yobiapi.comments.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.comments.dto.BoardCommentsDTO;
import org.example.yobiapi.comments.dto.RecipeCommentsDTO;
import org.example.yobiapi.contenttypes.Entity.ContentTypes;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.user.Entity.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "comment_id")
    private Integer commentId;

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
    @JoinColumn(name = "content_id", referencedColumnName = "content_id")
    private ContentTypes contentTypes;

    @Column(nullable = false, name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "create_content")
    private LocalDateTime createContent;

    @CreationTimestamp
    @Column(name = "update_content")
    private LocalDateTime updateContent;

    @ColumnDefault("0")
    @Column(name = "report_count")
    private Integer reportCount;

    @Column(name = "parent_comment_id")
    private Integer parentCommentId;

    public static Comments RecipeComments(RecipeCommentsDTO recipeCommentsDTO) {
        Comments comments = new Comments();
        comments.recipe.setRecipeId(recipeCommentsDTO.getRecipeId());
        comments.setContent(recipeCommentsDTO.getContent());
        comments.user.setUserId(recipeCommentsDTO.getUserId());
        comments.contentTypes.setContentId(recipeCommentsDTO.getContentId());
        return comments;
    }

    public static Comments BoardComments(BoardCommentsDTO boardCommentsDTO) {
        Comments comments = new Comments();
        comments.board.setBoardId(boardCommentsDTO.getBoardId());
        comments.user.setUserId(boardCommentsDTO.getUserId());
        comments.setContent(boardCommentsDTO.getContent());
        comments.contentTypes.setContentId(boardCommentsDTO.getContentId());

        return comments;
    }
}
