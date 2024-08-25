package org.example.yobiapi.comments.Entity;

import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.bookmark.Entity.Bookmark;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Comments findByCommentId(Integer commentId);
    List<CommentsRecipeProjection> findAllByRecipe(Recipe recipe);
    List<Comments> findAllByBoard(Board board);
}
