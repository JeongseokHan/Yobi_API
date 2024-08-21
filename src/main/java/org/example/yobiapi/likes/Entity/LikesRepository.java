package org.example.yobiapi.likes.Entity;

import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.bookmark.Entity.Bookmark;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByUserAndRecipe(User user, Recipe recipe);
    Integer countAllByRecipe(Recipe recipe);
    Likes findByUserAndBoard(User user, Board board);
    Integer countAllByBoard(Board board);
}
