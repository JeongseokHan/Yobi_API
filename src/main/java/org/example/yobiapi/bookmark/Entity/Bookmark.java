package org.example.yobiapi.bookmark.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.bookmark.dto.BoardBookmarkDTO;
import org.example.yobiapi.bookmark.dto.RecipeBookmarkDTO;
import org.example.yobiapi.recipe.Entity.Recipe;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookmark")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "bookmark_id")
    private Integer bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    public static Bookmark RecipeBookmark(RecipeBookmarkDTO recipeBookmarkDTO) {
        Bookmark bookmark = new Bookmark();
        bookmark.recipe.setRecipeId(recipeBookmarkDTO.getRecipeId());

        return bookmark;
    }

    public static Bookmark BoardBookmark(BoardBookmarkDTO boardBookmarkDTO) {
        Bookmark bookmark = new Bookmark();
        bookmark.board.setBoardId(boardBookmarkDTO.getBoardId());

        return bookmark;
    }
}
