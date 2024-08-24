package org.example.yobiapi.follow.Service;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.exception.CustomErrorCode;
import org.example.yobiapi.exception.CustomException;
import org.example.yobiapi.follow.Entity.Follow;
import org.example.yobiapi.follow.Entity.FolloweeProjection;
import org.example.yobiapi.follow.Entity.FollowerProjection;
import org.example.yobiapi.follow.Entity.FollowRepository;
import org.example.yobiapi.follow.dto.FollowDTO;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.user.Entity.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public HttpStatus saveFollow(FollowDTO followDTO) {
        User followee = userRepository.findByUserId(followDTO.getFolloweeId());
        if (followee == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        else {
            User follower = userRepository.findByUserId(followDTO.getFollowerId());
            if (follower == null) {
                throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
            }
            else {
                Follow follow = followRepository.findByFolloweeIdAndFollowerId(followee, follower);
                if(follow == null) {
                    Follow newFollow = Follow.toFollow(follower, followee);
                    followRepository.save(newFollow);
                    return HttpStatus.CREATED;
                }
                else {
                    followRepository.delete(follow);
                    return HttpStatus.OK;
                }

            }
        }
    }

    public List<FollowerProjection> searchFollower(String userId) { // 해당 아이디를 팔로우한 사람들을 검색
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        return followRepository.findAllByFollowerId(user);
    }

    public List<FolloweeProjection> searchFollowee(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        return followRepository.findAllByFolloweeId(user);
    }

    public Integer countFollower(String userId) { // 해당 유저를 팔로워한 사람들의 수를 반환
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        return followRepository.countAllByFollowerId(user);
    }

    public Integer countFollowee(String userId) { // 해당 유저가 팔로잉한 사람들의 수를 반환
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        return followRepository.countAllByFolloweeId(user);
    }
}
