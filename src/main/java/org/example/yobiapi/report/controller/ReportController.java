package org.example.yobiapi.report.controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.report.dto.BoardReportDTO;
import org.example.yobiapi.report.dto.CommentReportDTO;
import org.example.yobiapi.report.dto.RecipeReportDTO;
import org.example.yobiapi.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping(value = "/report/recipe")
    public ResponseEntity<?> insertRecipeReport(@RequestBody RecipeReportDTO recipeReportDTO) {
        return ResponseEntity.status(reportService.saveRecipeReport(recipeReportDTO)).build();
    }

    @PostMapping(value = "/report/board")
    public ResponseEntity<?> insertBoardReport(@RequestBody BoardReportDTO boardReportDTO) {
        return ResponseEntity.status(reportService.saveBoardReport(boardReportDTO)).build();
    }

    @PostMapping(value = "/report/comment")
    public ResponseEntity<?> insertCommentReport(@RequestBody CommentReportDTO commentReportDTO) {
        return ResponseEntity.status(reportService.saveCommentReport(commentReportDTO)).build();
    }

    @GetMapping(value = "/report/recipe/{page}/{size}")
    public ResponseEntity<?> getRecipeReport(@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(reportService.searchReportRecipe(page, size));
    }

    @GetMapping(value = "/report/board/{page}/{size}")
    public ResponseEntity<?> getBoardReport(@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(reportService.searchReportBoard(page, size));
    }

    @GetMapping(value = "/report/comment/{page}/{size}")
    public ResponseEntity<?> getCommentReport(@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(reportService.searchReportComment(page, size));
    }

    @GetMapping(value = "/report/all/{page}/{size}")
    public ResponseEntity<?> getAllReport(@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(reportService.searchReportAll(page, size));
    }
    @DeleteMapping(value = "/report/{reportId}")
    public ResponseEntity<?> deleteReport(@PathVariable("reportId") Integer reportId) {
        return ResponseEntity.status(reportService.deleteReport(reportId)).build();
    }

    @PatchMapping(value = "/report/{reportId}")
    public ResponseEntity<?> resolveReport(@PathVariable("reportId") Integer reportId) {
        return ResponseEntity.status(reportService.resolveReport(reportId)).build();
    }
}
