package org.example.yobiapi.likes.Service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.board.Entity.BoardRepository;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.likes.Entity.Likes;
import org.example.yobiapi.likes.Entity.LikesRepository;
import org.example.yobiapi.likes.dto.BoardLikesDTO;
import org.example.yobiapi.likes.dto.RecipeLikesDTO;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.recipe.Entity.RecipeRepository;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;

    public HttpStatus recipeLike(RecipeLikesDTO recipeLikesDTO) {
        User user = userRepository.findByUserId(recipeLikesDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Recipe recipe = recipeRepository.findByRecipeId(recipeLikesDTO.getRecipeId());
        if(recipe == null) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        Likes likes = likesRepository.findByUserAndRecipe(user, recipe);
        if(likes == null) {
            Likes newLikes = Likes.toLikesRecipe(user, recipe);
            likesRepository.save(newLikes);
            return HttpStatus.CREATED;
        }
        likesRepository.delete(likes);
        return HttpStatus.OK;
    }

    public Integer recipeLikeCount(Integer recipeId) {
        Recipe recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe == null) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        return likesRepository.countAllByRecipe(recipe);
    }

    public HttpStatus boardLike(BoardLikesDTO boardLikesDTO) {
        User user = userRepository.findByUserId(boardLikesDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Board board = boardRepository.findByBoardId(boardLikesDTO.getBoardId());
        if(board == null) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        Likes likes = likesRepository.findByUserAndBoard(user, board);
        if(likes == null) {
            Likes newLikes = Likes.toLikesBoard(user, board);
            likesRepository.save(newLikes);
            return HttpStatus.CREATED;
        }
        else {
            likesRepository.delete(likes);
            return HttpStatus.OK;
        }


    }

    public Integer boardLikeCount(Integer boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        if(board == null) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        return likesRepository.countAllByBoard(board);
    }
}
