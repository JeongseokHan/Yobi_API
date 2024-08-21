package org.example.yobiapi.likes.Controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.likes.Service.LikesService;
import org.example.yobiapi.likes.dto.BoardLikesDTO;
import org.example.yobiapi.likes.dto.RecipeLikesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping(value = "/like/recipe")
    public ResponseEntity<?> doRecipeLike(@RequestBody RecipeLikesDTO recipeLikesDTO) {
        return ResponseEntity.status(likesService.recipeLike(recipeLikesDTO)).build();
    }

    @GetMapping(value = "/like/recipe/{recipeId}")
    public ResponseEntity<?> searchRecipeLikeCount(@PathVariable("recipeId") Integer recipeId) {
        return ResponseEntity.status(200).body(likesService.recipeLikeCount(recipeId));
    }

    @PostMapping("like/board")
    public ResponseEntity<?> doBoardLike(@RequestBody BoardLikesDTO boardLikesDTO) {
        return ResponseEntity.status(likesService.boardLike(boardLikesDTO)).build();
    }

    @GetMapping("like/board/{boardId}")
    public ResponseEntity<?> searchBoardLikeCount(@PathVariable("boardId") Integer boardId) {
        return ResponseEntity.status(200).body(likesService.boardLikeCount(boardId));
    }
}
