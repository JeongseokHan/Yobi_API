package org.example.yobiapi.bookmark.Controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.bookmark.dto.RecipeBookmarkDTO;
import org.example.yobiapi.bookmark.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping(value = "/bookmark")
    public ResponseEntity<?> insertBookmark(@RequestBody RecipeBookmarkDTO recipeBookmarkDTO) {
        return ResponseEntity.status(bookmarkService.insertBookmark(recipeBookmarkDTO)).build();
    }

    @GetMapping(value = "/bookmark/user/{userId}/{page}/{size}")
    public ResponseEntity<?> getUserBookmark(
            @PathVariable("userId") String userId,
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(bookmarkService.searchUserBookmarkRecipe(userId, page, size));
    }
}
