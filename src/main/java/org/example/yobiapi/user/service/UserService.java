package org.example.yobiapi.user.service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.board.Entity.BoardRepository;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.follow.Entity.FollowRepository;
import org.example.yobiapi.follow.Service.FollowService;
import org.example.yobiapi.recipe.Entity.RecipeRepository;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.example.yobiapi.user.dto.ResponseUserProfileDTO;
import org.example.yobiapi.user.dto.SignInDTO;
import org.example.yobiapi.user.dto.UpdateUserNickNameDTO;
import org.example.yobiapi.user.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final RecipeRepository recipeRepository;
    private final FollowRepository followRepository;
    private final FollowService followService;

    public HttpStatus signUp(UserDTO userDTO) { // 회원가입
        User user = userRepository.findByUserId(userDTO.getUserId());
        if(user != null) {
            throw new CustomException(CustomErrorCode.ID_ALREADY_EXIST);
        }
        User newUser = User.toUser(userDTO);
        userRepository.save(newUser);
        return HttpStatus.OK;
    }

    public HttpStatus UpdateByUserNickName(UpdateUserNickNameDTO updateUserNickNameDTO) {
        User user = userRepository.findByUserId(updateUserNickNameDTO.getUserId());
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        User checkUser = userRepository.findByUserId(updateUserNickNameDTO.getUserId());
        if(checkUser != null) {
            throw new CustomException(CustomErrorCode.ID_ALREADY_EXIST);
        }
        if(updateUserNickNameDTO.getNickName().length() < 2) {
            throw new CustomException(CustomErrorCode.NICKNAME_SHORT_REQUEST);
        }
        else if(updateUserNickNameDTO.getNickName().length() > 10) {
            throw new CustomException(CustomErrorCode.NICKNAME_LONG_REQUEST);
        }
        user.setNickName(updateUserNickNameDTO.getNickName());
        userRepository.save(user);
        return HttpStatus.OK;
    }

    public HttpStatus singIn(String userId, String socialType) { // 로그인
        User user = userRepository.findByUserIdAndSocialType(userId, socialType);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        return HttpStatus.OK;
    }

    public HttpStatus delete(String userId, String socialType) { // 계정 삭제
        User user = userRepository.findByUserIdAndSocialType(userId, socialType);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        userRepository.delete(user);
        return HttpStatus.OK;
    }

    public ResponseUserProfileDTO getUserProfile(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Integer boardCount = boardRepository.countAllByUser(user);
        Integer recipeCount = recipeRepository.countAllByUser(user);

        return ResponseUserProfileDTO.builder()
                .name(user.getName())
                .userProfile(user.getUserProfile())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .boardCount(boardCount)
                .recipeCount(recipeCount)
                .build();
    }

}
