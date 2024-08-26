package org.example.yobiapi.report.service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.board.Entity.BoardRepository;
import org.example.yobiapi.comments.Entity.Comments;
import org.example.yobiapi.comments.Entity.CommentsRepository;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.recipe.Entity.RecipeRepository;
import org.example.yobiapi.report.Entity.Report;
import org.example.yobiapi.report.Entity.ReportRepository;
import org.example.yobiapi.report.dto.BoardReportDTO;
import org.example.yobiapi.report.dto.CommentReportDTO;
import org.example.yobiapi.report.dto.RecipeReportDTO;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final CommentsRepository commentsRepository;
    private final RecipeRepository recipeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public HttpStatus saveRecipeReport(RecipeReportDTO recipeReportDTO) {
        User user = userRepository.findByUserId(recipeReportDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Recipe recipe = recipeRepository.findByRecipeId(recipeReportDTO.getRecipeId());
        if(recipe == null) {
            throw new CustomException(CustomErrorCode.Recipe_NOT_FOUND);
        }
        if(recipeReportDTO.getReason() == null || recipeReportDTO.getReason().isEmpty()) {
            throw new CustomException(CustomErrorCode.REASON_IS_EMPTY);
        }
        if(recipeReportDTO.getReason().length() > 150) {
            throw new CustomException(CustomErrorCode.REASON_LONG_REQUEST);
        }
        Report report = Report.RecipeReport(recipe, user, recipeReportDTO);
        reportRepository.save(report);
        return HttpStatus.CREATED;
    }

    public HttpStatus saveCommentReport(CommentReportDTO commentReportDTO) {
        User user = userRepository.findByUserId(commentReportDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Comments comments = commentsRepository.findByCommentId(commentReportDTO.getCommentId());
        if(comments == null) {
            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
        }
        if(commentReportDTO.getReason() == null || commentReportDTO.getReason().isEmpty()) {
            throw new CustomException(CustomErrorCode.REASON_IS_EMPTY);
        }
        if(commentReportDTO.getReason().length() > 150) {
            throw new CustomException(CustomErrorCode.REASON_LONG_REQUEST);
        }
        Report report = Report.CommentReport(comments, user, commentReportDTO);
        reportRepository.save(report);
        return HttpStatus.CREATED;
    }

    public HttpStatus saveBoardReport(BoardReportDTO boardReportDTO) {
        User user = userRepository.findByUserId(boardReportDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Board board = boardRepository.findByBoardId(boardReportDTO.getBoardId());
        if(board == null) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        if(boardReportDTO.getReason() == null || boardReportDTO.getReason().isEmpty()) {
            throw new CustomException(CustomErrorCode.REASON_IS_EMPTY);
        }
        if(boardReportDTO.getReason().length() > 150) {
            throw new CustomException(CustomErrorCode.REASON_LONG_REQUEST);
        }
        Report report = Report.BoardReport(board, user, boardReportDTO);
        reportRepository.save(report);
        return HttpStatus.CREATED;
    }
}
