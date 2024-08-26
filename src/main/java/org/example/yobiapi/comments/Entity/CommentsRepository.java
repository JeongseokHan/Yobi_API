package org.example.yobiapi.comments.Entity;

import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Comments findByCommentId(Integer commentId);
    List<CommentsRecipeProjection> findAllByRecipeAndCommentIdIsNull(Recipe recipe); // 레시피의 댓글 반환
    List<CommentsBoardProjection> findAllByBoardAndCommentIdIsNull(Board board); // 게시판의 댓글 반환
    List<CommentsRecipeProjection> findAllByRecipeAndParentCommentId(Recipe recipe, Integer parentCommentId); // 레시피의 대댓글 반환
    List<CommentsBoardProjection> findAllByBoardAndParentCommentId(Board board, Integer parentCommentId); // 게시판의 대댓글 반환
    List<CommentsChildProjection> findAllByParentCommentId(Integer parentCommentId); // 대댓글 반환
}
