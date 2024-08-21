package org.example.yobiapi.likes.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.user.Entity.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "like_id")
    private Integer likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    @CreationTimestamp
    @Column(nullable = false, name = "like_date")
    private LocalDateTime likeDate;

    public static Likes toLikesBoard(User user, Board board) {
        Likes likes = new Likes();
        likes.setBoard(board);
        likes.setUser(user);
        return likes;
    }

    public static Likes toLikesRecipe(User user, Recipe recipe) {
        Likes likes = new Likes();
        likes.setRecipe(recipe);
        likes.setUser(user);
        return likes;
    }
}
