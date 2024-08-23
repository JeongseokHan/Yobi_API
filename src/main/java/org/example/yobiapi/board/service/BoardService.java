package org.example.yobiapi.board.service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.board.Entity.BoardProjection;
import org.example.yobiapi.board.Entity.BoardRepository;
import org.example.yobiapi.board.dto.BoardDTO;
import org.example.yobiapi.board.dto.DeleteBoardDTO;
import org.example.yobiapi.board.dto.UpdateBoardDTO;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public Integer saveBoard(BoardDTO boardDTO) {
        User user = userRepository.findByUserId(boardDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
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
            else {
                Board board = Board.toBoard(boardDTO, user);
                boardRepository.save(board);
                return board.getBoardId();
            }
        }
    }

    public HttpStatus updateBoard(Integer boardId , UpdateBoardDTO updateBoardDTO) {
        User user = userRepository.findByUserId(updateBoardDTO.getUserId());
        Board updatedBoard = boardRepository.findByBoardId(boardId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            if(updatedBoard == null) {
                throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
            }
            else {
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
                else {
                    updatedBoard.setTitle(updateBoardDTO.getTitle());
                    updatedBoard.setContent(updateBoardDTO.getContent());
                    updatedBoard.setCategory(updateBoardDTO.getCategory());
                    updatedBoard.setUpdatedDate(LocalDateTime.now());
                    updatedBoard.setThumbnail(updateBoardDTO.getThumbnail());
                    boardRepository.save(updatedBoard);
                    return HttpStatus.OK;
                }
            }
        }
    }

    public HttpStatus deleteBoard(Integer boardId, DeleteBoardDTO deleteBoardDTO) {
        User user = userRepository.findByUserId(deleteBoardDTO.getUserId());
        Board deletedBoard = boardRepository.findByBoardId(boardId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            if(deletedBoard == null) {
                throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
            }
            else {
                boardRepository.delete(deletedBoard);
                return HttpStatus.OK;
            }
        }
    }

    public List<BoardProjection> searchBoard_Title(String title) {
        List<BoardProjection> boards = boardRepository.findAllByTitleContaining(title);
        if(boards.isEmpty()) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        else {
            return boards;
        }
    }

    public List<BoardProjection> searchBoard_Category(String category) {
        List<BoardProjection> boards = boardRepository.findAllByCategoryContaining(category);
        if(boards.isEmpty()) {
            throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
        }
        else {
            return boards;
        }
    }

    public List<BoardProjection> searchBoard_User(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            List<BoardProjection> boards = boardRepository.findAllByUser(user);
            if(boards.isEmpty()) {
                throw new CustomException(CustomErrorCode.Board_NOT_FOUND);
            }
            else {
                return boards;
            }
        }
    }

}
