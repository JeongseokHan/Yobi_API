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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public HttpStatus saveFollow(FollowDTO followDTO) {
        User followee = userRepository.findByUserId(followDTO.getFolloweeId()); // 팔로우 하는 사람
        if (followee == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        User follower = userRepository.findByUserId(followDTO.getFollowerId()); // 팔로우를 받는 사람
        if (follower == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        Follow follow = followRepository.findByFolloweeIdAndFollowerId(followee, follower);
        if(follow == null) {
            Follow newFollow = Follow.toFollow(follower, followee);
            if(follower.getFollowersCount() == null) follower.setFollowersCount(0);
            if(followee.getFollowingCount() == null) followee.setFollowingCount(0);
            follower.setFollowersCount(follower.getFollowersCount() + 1);
            followee.setFollowingCount(followee.getFollowingCount() + 1);
            followRepository.save(newFollow);
            userRepository.save(follower);
            userRepository.save(followee);
            return HttpStatus.CREATED;
        }
        else {
            follower.setFollowersCount(follower.getFollowersCount() - 1);
            followee.setFollowingCount(followee.getFollowingCount() - 1);
            followRepository.delete(follow);
            userRepository.save(follower);
            userRepository.save(followee);
            return HttpStatus.OK;
        }

    }

    public Page<FollowerProjection> searchFollower(String userId, int page, int size) { // 해당 아이디를 팔로우한 사람들을 검색
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        return followRepository.findAllByFollowerId(user, pageable);
    }

    public Page<FolloweeProjection> searchFollowee(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        return followRepository.findAllByFolloweeId(user, pageable);
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
