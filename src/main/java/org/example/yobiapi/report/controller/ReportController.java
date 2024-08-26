package org.example.yobiapi.report.controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.report.dto.BoardReportDTO;
import org.example.yobiapi.report.dto.CommentReportDTO;
import org.example.yobiapi.report.dto.RecipeReportDTO;
import org.example.yobiapi.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
