package org.example.yobiapi.bookmark.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.bookmark.dto.RecipeBookmarkDTO;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.user.Entity.User;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    public static Bookmark RecipeBookmark(Recipe recipe, User user) {
        Bookmark bookmark = new Bookmark();
        bookmark.setRecipe(recipe);
        bookmark.setUser(user);
        return bookmark;
    }
}
