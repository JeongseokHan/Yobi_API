package org.example.yobiapi.comments.Service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.board.Entity.BoardRepository;
import org.example.yobiapi.comments.Entity.*;
import org.example.yobiapi.comments.dto.*;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.recipe.Entity.RecipeRepository;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final RecipeRepository recipeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public HttpStatus saveRecipeComment(RecipeCommentsDTO recipeCommentsDTO) {
        User user = userRepository.findByUserId(recipeCommentsDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Recipe recipe = recipeRepository.findByRecipeId(recipeCommentsDTO.getRecipeId());
        if(recipe == null) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        if(recipeCommentsDTO.getContent() == null || recipeCommentsDTO.getContent().isEmpty()) {
            throw new CustomException(CustomErrorCode.Content_Is_Empty);
        }
        if(recipeCommentsDTO.getContent().length() > 200) {
            throw new CustomException(CustomErrorCode.CONTENT_LONG_REQUEST);
        }
        if(recipeCommentsDTO.getParentCommentId() == null) {
            Comments comments = Comments.recipeComments(recipeCommentsDTO, recipe, user);
            commentsRepository.save(comments);
            return HttpStatus.CREATED;
        }
        else {
            Comments comments = Comments.childRecipeComments(recipeCommentsDTO, recipe, user);
            commentsRepository.save(comments);
            return HttpStatus.CREATED;
        }
    }

    public HttpStatus saveBoardComment(BoardCommentsDTO boardCommentsDTO) {
        User user = userRepository.findByUserId(boardCommentsDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Board board = boardRepository.findByBoardId(boardCommentsDTO.getBoardId());
        if(board == null) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        if(boardCommentsDTO.getContent() == null || boardCommentsDTO.getContent().isEmpty()) {
            throw new CustomException(CustomErrorCode.Content_Is_Empty);
        }
        if(boardCommentsDTO.getContent().length() > 200) {
            throw new CustomException(CustomErrorCode.CONTENT_LONG_REQUEST);
        }
        if(boardCommentsDTO.getParentCommentId() == null) {
            Comments comments = Comments.boardComments(boardCommentsDTO, board, user);
            commentsRepository.save(comments);
            return HttpStatus.CREATED;
        }
        else {
            Comments comments = Comments.childBoardComments(boardCommentsDTO, board, user);
            commentsRepository.save(comments);
            return HttpStatus.CREATED;
        }
    }

    public HttpStatus deleteComment(Integer commentId, DeleteCommentsDTO deleteCommentsDTO) {
        Comments comments = commentsRepository.findByCommentId(commentId);
        if(comments == null) {
            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
        }
        User user = userRepository.findByUserId(deleteCommentsDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        if(!comments.getUser().equals(user)) {
            throw new CustomException(CustomErrorCode.AUTHOR_NOT_EQUAL);
        }
        commentsRepository.delete(comments);
        return HttpStatus.OK;
    }

    public Page<CommentsRecipeProjection> recipeCommentList(Integer recipeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Recipe recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe == null) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        Page<CommentsRecipeProjection> comments = commentsRepository.findAllByRecipeAndCommentIdIsNull(recipe, pageable);
        if(comments.isEmpty()) {
            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
        }
        return comments;
    }

    public Page<CommentsBoardProjection> boardCommentList(Integer boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Board board = boardRepository.findByBoardId(boardId);
        if(board == null) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        Page<CommentsBoardProjection> comments = commentsRepository.findAllByBoardAndCommentIdIsNull(board, pageable);
        if(comments.isEmpty()) {
            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
        }
        return comments;
    }

    public Page<CommentsChildProjection> childCommentList(Integer commentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentsChildProjection> comments = commentsRepository.findAllByParentCommentId(commentId, pageable);
        if(comments.isEmpty()) {
            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
        }
        return comments;
    }

//    public List<CommentsRecipeProjection> recipeChildCommentList(RecipeChildCommentsDTO recipeChildCommentsDTO) {
//        Recipe recipe = recipeRepository.findByRecipeId(recipeChildCommentsDTO.getRecipeId());
//        if(recipe == null) {
//            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
//        }
//        List<CommentsRecipeProjection> comments = commentsRepository.findAllByRecipeAndParentCommentId(recipe, recipeChildCommentsDTO.getCommentId());
//        if(comments.isEmpty()) {
//            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
//        }
//        return comments;
//    }
//
//    public List<CommentsBoardProjection> boardChildCommentList(BoardChildCommentsDTO boardChildCommentsDTO) {
//        Board board = boardRepository.findByBoardId(boardChildCommentsDTO.getBoardId());
//        if(board == null) {
//            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
//        }
//        List<CommentsBoardProjection> comments = commentsRepository.findAllByBoardAndParentCommentId(board, boardChildCommentsDTO.getCommentId());
//        if(comments.isEmpty()) {
//            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
//        }
//        return comments;
//    }
}
