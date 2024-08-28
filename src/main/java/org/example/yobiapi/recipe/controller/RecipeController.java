package org.example.yobiapi.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.recipe.dto.DeleteRecipeDTO;
import org.example.yobiapi.recipe.dto.RecipeDTO;
import org.example.yobiapi.recipe.dto.UpdateRecipeDTO;
import org.example.yobiapi.recipe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping(value = "/recipe/def")
    public ResponseEntity<?> insertDefaultRecipe() {
        return ResponseEntity.status(recipeService.fetchDataAndPrint()).build();
    }

    @PostMapping(value = "/recipe")
    public ResponseEntity<?> insertRecipe(@RequestBody RecipeDTO recipeDTO) {
        Integer recipeId = recipeService.saveRecipe(recipeDTO);
        return ResponseEntity.status(201).body(recipeId);
    }

    @PutMapping(value = "/recipe/{recipeId}")
    public ResponseEntity<?> updateRecipe(@PathVariable("recipeId") Integer recipeId,@RequestBody UpdateRecipeDTO updateRecipeDTO) {
        return ResponseEntity.status(recipeService.updateRecipe(recipeId, updateRecipeDTO)).build();
    }

    @DeleteMapping(value = "/recipe/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("recipeId") Integer recipeId, @RequestBody DeleteRecipeDTO deleteRecipeDTO) {
        return ResponseEntity.status(recipeService.deleteRecipe(recipeId, deleteRecipeDTO)).build();
    }

    @GetMapping(value = "/recipe/category/{category}/{page}/{size}")
    public ResponseEntity<?> getRecipeByCategory(
            @PathVariable("category") String category,
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.ok().body(recipeService.SearchRecipe_Category(category, page, size));
    }

    @GetMapping(value = "/recipe/title/{title}/{page}/{size}")
    public ResponseEntity<?> getRecipeByTitle(
            @PathVariable("title") String title,
            @PathVariable("page") int page,
            @PathVariable("size") int size
    ) {
        return ResponseEntity.status(200).body(recipeService.SearchRecipe_Title(title, page, size));
    }

    @GetMapping(value = "/recipe/user/{userId}/{page}/{size}")
    public ResponseEntity<?> getRecipeByUser(
            @PathVariable("userId") String userId,
            @PathVariable("page") int page,
            @PathVariable("size") int size
    ) {
        return ResponseEntity.status(200).body(recipeService.SearchRecipe_User(userId, page, size));
    }

    @GetMapping("/recipe/all/{page}/{size}")
    public ResponseEntity<?> getRecipeByAll(
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(recipeService.SearchRecipe_All(page, size));
    }
}
