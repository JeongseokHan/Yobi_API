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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

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

    public static Comments recipeComments(RecipeCommentsDTO recipeCommentsDTO, Recipe recipe, User user) {
        Comments comments = new Comments();
        comments.setRecipe(recipe);
        comments.setContent(recipeCommentsDTO.getContent());
        comments.setUser(user);
        return comments;
    }

    public static Comments boardComments(BoardCommentsDTO boardCommentsDTO, Board board, User user) {
        Comments comments = new Comments();
        comments.setBoard(board);
        comments.setUser(user);
        comments.setContent(boardCommentsDTO.getContent());
        return comments;
    }

    public static Comments childRecipeComments(RecipeCommentsDTO recipeCommentsDTO, Recipe recipe, User user) {
        Comments comments = recipeComments(recipeCommentsDTO, recipe, user);
        comments.setParentCommentId(recipeCommentsDTO.getParentCommentId());
        return comments;
    }

    public static Comments childBoardComments(BoardCommentsDTO boardCommentsDTO, Board board, User user) {
        Comments comments = boardComments(boardCommentsDTO, board, user);
        comments.setParentCommentId(boardCommentsDTO.getParentCommentId());
        return comments;
    }

}
