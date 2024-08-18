package org.example.yobiapi.bookmark.Entity;

import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByUserAndRecipe(User user, Recipe recipe);
    List<Bookmark> findAllByUser(User user);
}
