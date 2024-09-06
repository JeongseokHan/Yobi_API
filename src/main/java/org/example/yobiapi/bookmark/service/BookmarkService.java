package org.example.yobiapi.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.bookmark.Entity.Bookmark;
import org.example.yobiapi.bookmark.Entity.BookmarkRepository;
import org.example.yobiapi.bookmark.dto.RecipeBookmarkDTO;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.recipe.Entity.RecipeProjection;
import org.example.yobiapi.recipe.Entity.RecipeRepository;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public HttpStatus insertBookmark(RecipeBookmarkDTO recipeBookmarkDTO) {
        User user = userRepository.findByUserId(recipeBookmarkDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Recipe recipe = recipeRepository.findByRecipeId(recipeBookmarkDTO.getRecipeId());
        if(recipe == null) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        Bookmark bookmark = bookmarkRepository.findByUserAndRecipe(user, recipe);
        if(bookmark == null) {
            Bookmark newBookmark = Bookmark.RecipeBookmark(recipe, user);
            bookmarkRepository.save(newBookmark);
            return HttpStatus.CREATED;
        }
            bookmarkRepository.delete(bookmark);
            return HttpStatus.OK;
    }

    public Page<RecipeProjection> searchUserBookmarkRecipe(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUser(user);
        if(bookmarks.isEmpty()) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        List<Integer> recipes = bookmarks.stream()
                .map(bookmark -> bookmark.getRecipe().getRecipeId())
                .collect(Collectors.toList());
        return recipeRepository.findAllByRecipeIdIn(recipes, pageable);
    }

    public Integer userBookmarkCount(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        return bookmarkRepository.countByUser(user);
    }

}
