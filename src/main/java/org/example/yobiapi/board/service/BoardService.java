package org.example.yobiapi.board.service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.board.Entity.BoardProjection;
import org.example.yobiapi.board.Entity.BoardRepository;
import org.example.yobiapi.board.dto.BoardDTO;
import org.example.yobiapi.board.dto.DeleteBoardDTO;
import org.example.yobiapi.board.dto.UpdateBoardDTO;
import org.example.yobiapi.content_manual.Entity.ContentType;
import org.example.yobiapi.content_manual.Entity.Content_Manual;
import org.example.yobiapi.content_manual.Entity.Content_ManualRepository;
import org.example.yobiapi.content_manual.dto.Content_ManualDTO;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final Content_ManualRepository contentManualRepository;

    public Integer saveBoard(BoardDTO boardDTO) {
        User user = userRepository.findByUserId(boardDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        if(boardDTO.getTitle().length() > 45) {
            throw new CustomException(CustomErrorCode.Title_LONG_REQUEST);
        }
        else if(boardDTO.getContent().isEmpty()) {
            throw new CustomException(CustomErrorCode.Content_Is_Empty);
        }
        else if(boardDTO.getCategory().isEmpty()) {
            throw new CustomException(CustomErrorCode.CATEGORY_IS_EMPTY);
        }
        else if(boardDTO.getCategory().length() > 45) {
            throw new CustomException(CustomErrorCode.CATEGORY_LONG_REQUEST);
        }
        Board board = Board.toBoard(boardDTO, user);
        boardRepository.save(board);
        return board.getBoardId();
    }

    public HttpStatus updateBoard(Integer boardId , UpdateBoardDTO updateBoardDTO) {
        User user = userRepository.findByUserId(updateBoardDTO.getUserId());
        Board updatedBoard = boardRepository.findByBoardId(boardId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        if(updatedBoard == null) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        if(updateBoardDTO.getTitle().length() > 45) {
            throw new CustomException(CustomErrorCode.Title_LONG_REQUEST);
        }
        else if(updateBoardDTO.getContent() == null) {
            throw new CustomException(CustomErrorCode.Content_Is_Empty);
        }
        else if(updateBoardDTO.getCategory().isEmpty()) {
            throw new CustomException(CustomErrorCode.CATEGORY_IS_EMPTY);
        }
        else if(updateBoardDTO.getCategory().length() > 45) {
            throw new CustomException(CustomErrorCode.CATEGORY_LONG_REQUEST);
        }
        updatedBoard.setTitle(updateBoardDTO.getTitle());
        updatedBoard.setContent(updateBoardDTO.getContent());
        updatedBoard.setCategory(updateBoardDTO.getCategory());
        updatedBoard.setUpdatedDate(LocalDateTime.now());
        updatedBoard.setBoardThumbnail(updateBoardDTO.getBoardThumbnail());
        boardRepository.save(updatedBoard);
        return HttpStatus.OK;
    }

    public HttpStatus deleteBoard(Integer boardId, DeleteBoardDTO deleteBoardDTO) {
        User user = userRepository.findByUserId(deleteBoardDTO.getUserId());
        Board deletedBoard = boardRepository.findByBoardId(boardId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        if(deletedBoard == null) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        boardRepository.delete(deletedBoard);
        return HttpStatus.OK;
    }

    public Page<BoardProjection> searchBoard_Title(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardProjection> boards = boardRepository.findAllByTitleContaining(title, pageable);
        if(boards.isEmpty()) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        return boards;
    }

    public Page<BoardProjection> searchBoard_Category(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardProjection> boards = boardRepository.findAllByCategoryContaining(category, pageable);
        if(boards.isEmpty()) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        return boards;
    }

    public Page<BoardProjection> searchBoard_User(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Page<BoardProjection> boards = boardRepository.findAllByUser(user, pageable);
        if(boards.isEmpty()) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        return boards;
    }

    public Page<BoardProjection> searchBoard_HighViewList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardProjection> boards = boardRepository.findAllByOrderByViewsDesc(pageable);
        if(boards.isEmpty()) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        return boards;
    }

    public Page<BoardProjection> searchBoard_All(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardProjection> boards = boardRepository.findAllBy(pageable);
        if(boards.isEmpty()) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        return boards;
    }

    public BoardDTO getBoardWithManuals(Integer boardId) {
        // 게시판 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.Board_NOT_FOUND));

        // 게시판에 연결된 Content_Manual 조회
        List<Content_Manual> manuals = contentManualRepository.findByContentTypeAndContentId(ContentType.BOARD, boardId);

        // Content_Manual 데이터를 ContentManualDTO로 변환
        List<Content_ManualDTO> manualDTOs = manuals.stream()
                .map(manual -> {
                    Content_ManualDTO manualDTO = new Content_ManualDTO();
                    manualDTO.setManualId(manual.getManualId());
                    manualDTO.setStepNumber(manual.getStepNumber());
                    manualDTO.setDescription(manual.getDescription());
                    manualDTO.setImage(manual.getImage());
                    return manualDTO;
                }).collect(Collectors.toList());

        // Board 데이터를 BoardDTO로 변환
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardId(board.getBoardId());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setContent(board.getContent());
        boardDTO.setCategory(board.getCategory());
        boardDTO.setViews(board.getViews());
        boardDTO.setReportCount(board.getReportCount());
        boardDTO.setCreateDate(board.getCreateDate());
        boardDTO.setUpdateDate(board.getUpdatedDate());
        boardDTO.setBoardThumbnail(board.getBoardThumbnail());
        boardDTO.setManuals(manualDTOs);

        return boardDTO;
    }

}