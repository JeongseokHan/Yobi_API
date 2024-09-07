package org.example.yobiapi.view_log.Entity;

import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface View_LogRepository extends JpaRepository<View_Log, Integer> {
    Optional<View_Log> findByUserAndRecipe(User user, Recipe recipe);
    Optional<View_Log> findByUserAndBoard(User user, Board board);
}
