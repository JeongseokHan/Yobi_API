package org.example.yobiapi.user.service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.example.yobiapi.user.dto.SignInDTO;
import org.example.yobiapi.user.dto.UpdateUserNickNameDTO;
import org.example.yobiapi.user.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public HttpStatus signUp(UserDTO userDTO) { // 회원가입
        User user = userRepository.findByUserId(userDTO.getUserId());
        User userNickName = userRepository.findByNickName(userDTO.getNickName());
        if(user != null) {
            throw new CustomException(CustomErrorCode.ID_ALREADY_EXIST);
        }
        else {
            if(userDTO.getUserId().length() > 15) {
                throw new CustomException(CustomErrorCode.ID_LONG_REQUEST);
            }
            else if(userNickName != null) {
                throw new CustomException(CustomErrorCode.NICKNAME_ALREADY_EXIST);
            }
            else if(userDTO.getPassWord().length() > 15) {
                throw new CustomException(CustomErrorCode.PW_LONG_REQUEST);
            }
            else if(userDTO.getName().length() > 10) {
                throw new CustomException(CustomErrorCode.NICKNAME_LONG_REQUEST);
            }
            else if(userDTO.getUserId().length() < 6) {
                throw new CustomException(CustomErrorCode.ID_SHORT_REQUEST);
            }
            else if(userDTO.getPassWord().length() < 6) {
                throw new CustomException(CustomErrorCode.PW_SHORT_REQUEST);
            }
            else if(userDTO.getName().length() < 2) {
                throw new CustomException(CustomErrorCode.NICKNAME_SHORT_REQUEST);
            }
            else {
                User newUser = User.toUser(userDTO);
                userRepository.save(newUser);
                return HttpStatus.OK;
            }
        }
    }

    public HttpStatus UpdateByUserNickName(UpdateUserNickNameDTO updateUserNickNameDTO) {
        User user = userRepository.findByUserId(updateUserNickNameDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            if(updateUserNickNameDTO.getNickName().length() < 2) {
                throw new CustomException(CustomErrorCode.NICKNAME_SHORT_REQUEST);
            }
            else if(updateUserNickNameDTO.getNickName().length() > 10) {
                throw new CustomException(CustomErrorCode.NICKNAME_LONG_REQUEST);
            }
            else {
                user.setNickName(updateUserNickNameDTO.getNickName());
                userRepository.save(user);
                return HttpStatus.OK;
            }
        }
    }

    public HttpStatus singIn(SignInDTO signInDTO) { // 로그인
        User user = userRepository.findByUserIdAndPassWord(signInDTO.getUserId(), signInDTO.getPassWord());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            return HttpStatus.OK;
        }
    }

    public HttpStatus delete(SignInDTO signInDTO) { // 계정 삭제
        User user = userRepository.findByUserIdAndPassWord(signInDTO.getUserId(), signInDTO.getPassWord());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            userRepository.delete(user);
            return HttpStatus.OK;
        }
    }

}
