package org.example.yobiapi.recipe.Entity;

import org.example.yobiapi.user.Entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByRecipeId(Integer recipeId);
    List<RecipeProjection> findAllByCategoryContaining(String category);
    List<RecipeProjection> findAllByUser(User user);
    List<RecipeProjection> findAllByTitleContaining(String title);
    List<RecipeProjection> findAllByRecipeIdIn(List<Integer> recipeId);

}
