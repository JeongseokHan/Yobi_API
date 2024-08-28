package org.example.yobiapi.follow.Controller;

import lombok.RequiredArgsConstructor;
import org.example.yobiapi.follow.Service.FollowService;
import org.example.yobiapi.follow.dto.FollowDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping(value = "/follow")
    public ResponseEntity<?> following(@RequestBody FollowDTO followDTO) {
        return ResponseEntity.status(followService.saveFollow(followDTO)).build();
    }

    @GetMapping(value = "/follow/0/{userId}/{page}/{size}") // 해당 유저를 팔로우한 사람들을 검색
    public ResponseEntity<?> getFollower(
            @PathVariable("userId") String userId,
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.status(200).body(followService.searchFollower(userId, page, size));
    }

    @GetMapping(value = "/follow/1/{userId}/{page}/{size}")
    public ResponseEntity<?> getFollowing(
            @PathVariable("userId") String userId,
            @PathVariable("page") int page,
            @PathVariable("size") int size) { // 해당 유저가 팔로잉한 사람들을 검색
        return ResponseEntity.status(200).body(followService.searchFollowee(userId, page, size));
    }

    @GetMapping(value = "/follow/2/{userId}")
    public ResponseEntity<?> getFollowerCount(@PathVariable("userId") String userId) { // 해당 유저의 팔로워 수를 반환
        return ResponseEntity.status(200).body(followService.countFollower(userId));
    }

    @GetMapping(value = "/follow/3/{userId}")
    public ResponseEntity<?> getFollowingCount(@PathVariable("userId") String userId) { // 해당 유저의 팔로잉 수를 반환
        return ResponseEntity.status(200).body(followService.countFollowee(userId));
    }
}
