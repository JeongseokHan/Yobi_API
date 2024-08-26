package org.example.yobiapi.comments.Controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.comments.Service.CommentService;
import org.example.yobiapi.comments.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(value = "/comments/recipe")
    public ResponseEntity<?> saveRecipeComment(@RequestBody RecipeCommentsDTO recipeCommentsDTO) {
        return ResponseEntity.status(commentService.saveRecipeComment(recipeCommentsDTO)).build();
    }

    @PostMapping(value = "/comments/board")
    public ResponseEntity<?> saveBoardComment(@RequestBody BoardCommentsDTO boardCommentsDTO) {
        return ResponseEntity.status(commentService.saveBoardComment(boardCommentsDTO)).build();
    }

    @DeleteMapping(value = "/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Integer commentId, @RequestBody DeleteCommentsDTO deleteCommentsDTO) {
        return ResponseEntity.status(commentService.deleteComment(commentId, deleteCommentsDTO)).build();
    }

    @GetMapping(value = "/comments/recipe/{recipeId}")
    public ResponseEntity<?> getRecipeComments(@PathVariable("recipeId") Integer recipeId) {
        return ResponseEntity.status(200).body(commentService.recipeCommentList(recipeId));
    }

    @GetMapping(value = "/comments/board/{boardId}")
    public ResponseEntity<?> getBoardComments(@PathVariable("boardId") Integer boardId) {
        return ResponseEntity.status(200).body(commentService.boardCommentList(boardId));
    }

    @GetMapping(value = "/comments/child/{parentCommentId}")
    public ResponseEntity<?> getChildComments(@PathVariable("parentCommentId") Integer parentCommentId) {
        return ResponseEntity.status(200).body(commentService.childCommentList(parentCommentId));
    }



//    @GetMapping(value = "/comments/recipe/child")
//    public ResponseEntity<?> getChildRecipeComments(@RequestBody RecipeChildCommentsDTO recipeChildCommentsDTO) {
//        return ResponseEntity.status(200).body(commentService.recipeChildCommentList(recipeChildCommentsDTO));
//    }
//
//    @GetMapping(value = "/comments/board/child")
//    public ResponseEntity<?> getChildBoardComments(@RequestBody BoardChildCommentsDTO boardChildCommentsDTO) {
//        return ResponseEntity.status(200).body(commentService.boardChildCommentList(boardChildCommentsDTO));
//    }
}
