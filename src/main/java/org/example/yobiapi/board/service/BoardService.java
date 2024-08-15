package org.example.yobiapi.board.service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.board.Entity.BoardRepository;
import org.example.yobiapi.board.dto.BoardDTO;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.springframework.stereotype.Service;

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
            if(boardDTO.getUserId().length() > 15) {
                throw new CustomException(CustomErrorCode.ID_LONG_REQUEST);
            }
            else if(boardDTO.getTitle().length() > 45) {
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

}
