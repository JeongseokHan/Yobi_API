package org.example.yobiapi.board.controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.board.dto.BoardDTO;
import org.example.yobiapi.board.dto.DeleteBoardDTO;
import org.example.yobiapi.board.dto.UpdateBoardDTO;
import org.example.yobiapi.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping(value = "/board")
    public ResponseEntity<?> insertBoard(@RequestBody BoardDTO boardDTO) {
        return ResponseEntity.status(201).body(boardService.saveBoard(boardDTO));
    }

    @PutMapping(value = "/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable("boardId") Integer boardId, @RequestBody UpdateBoardDTO updateBoardDTO) {
        return ResponseEntity.status(boardService.updateBoard(boardId, updateBoardDTO)).build();
    }

    @GetMapping(value = "/board/title/{title}/{page}/{size}")
    public ResponseEntity<?> getBoardByTitle(
            @PathVariable("title") String title,
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(boardService.searchBoard_Title(title, page, size));
    }

    @GetMapping(value = "/board/category/{category}/{page}/{size}")
    public ResponseEntity<?> getBoardByCategory(
            @PathVariable("category") String category,
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(boardService.searchBoard_Category(category, page, size));
    }

    @GetMapping(value = "/board/user/{userId}/{page}/{size}")
    public ResponseEntity<?> getBoardByUserId(
            @PathVariable("userId") String userId,
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(boardService.searchBoard_User(userId, page, size));
    }

    @DeleteMapping(value = "/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable("boardId") Integer boardId, @RequestBody DeleteBoardDTO deleteBoardDTO) {
        return ResponseEntity.status(boardService.deleteBoard(boardId, deleteBoardDTO)).build();
    }

    @GetMapping(value = "/board/view/{page}/{size}")
    public ResponseEntity<?> getBoardByHighView(@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(boardService.searchBoard_HighViewList(page, size));
    }

    @GetMapping(value = "/board/all/{page}/{size}")
    public ResponseEntity<?> getBoardByAll(@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(boardService.searchBoard_All(page, size));
    }

    @GetMapping(value = "/board/{boardId}")
    public ResponseEntity<?> getBoardById(@PathVariable("boardId") Integer boardId) {
        BoardDTO boardDTO = boardService.getBoardWithManuals(boardId);
        return ResponseEntity.ok().body(boardDTO);
    }
}
