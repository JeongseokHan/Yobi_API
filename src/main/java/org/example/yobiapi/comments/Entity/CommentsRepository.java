package org.example.yobiapi.comments.Entity;

import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Comments findByCommentId(Integer commentId);
    Page<CommentsRecipeProjection> findAllByRecipeAndCommentIdIsNull(Recipe recipe, Pageable pageable); // 레시피의 댓글 반환
    Page<CommentsBoardProjection> findAllByBoardAndCommentIdIsNull(Board board, Pageable pageable); // 게시판의 댓글 반환환
    Page<CommentsChildProjection> findAllByParentCommentId(Integer parentCommentId, Pageable pageable); // 대댓글 반환
}
