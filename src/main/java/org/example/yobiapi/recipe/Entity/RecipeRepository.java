package org.example.yobiapi.recipe.Entity;

import org.example.yobiapi.user.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByRecipeId(Integer recipeId);
    Page<RecipeProjection> findAllByCategoryContaining(String category, Pageable pageable);
    Page<RecipeProjection> findAllByUser(User user, Pageable pageable);
    Page<RecipeProjection> findAllByTitleContaining(String title, Pageable pageable);
    Page<RecipeProjection> findAllByRecipeIdIn(List<Integer> recipeId, Pageable pageable);
    Page<RecipeProjection> findAllBy(Pageable pageable);
    Integer countAllByUser(User user);

}
