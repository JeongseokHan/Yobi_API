package org.example.yobiapi.recipe.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.recipe.dto.RecipeDTO;
import org.example.yobiapi.user.Entity.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "recipe_id")
    private Integer recipeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "category")
    private String category;

    @Column(nullable = false, name = "ingredient")
    private String ingredient;

    @ColumnDefault("0")
    @Column(name = "views")
    private Integer views;

    @ColumnDefault("0")
    @Column(name = "report_count")
    private Integer reportCount;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @CreationTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "recipe_thumbnail")
    private String recipeThumbnail;

    public static Recipe toRecipe(RecipeDTO recipeDTO, User user) {
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        recipe.setCategory(recipeDTO.getCategory());
        recipe.setIngredient(recipeDTO.getIngredient());
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setRecipeThumbnail(recipeDTO.getRecipeThumbnail());
        return recipe;
    }
}
