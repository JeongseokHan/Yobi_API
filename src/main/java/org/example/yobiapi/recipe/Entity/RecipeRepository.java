package org.example.yobiapi.recipe.Entity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByRecipeId(Integer recipeId);
    List<Recipe> findAllByTitleContaining(String title);
    List<Recipe> findAllByCategoryContaining(String category);

    List<Recipe> findAllByUserId(String userId);

}
