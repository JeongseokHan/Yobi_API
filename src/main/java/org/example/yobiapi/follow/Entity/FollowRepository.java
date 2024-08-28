package org.example.yobiapi.follow.Entity;

import org.example.yobiapi.user.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Page<FolloweeProjection> findAllByFolloweeId(User user, Pageable pageable); // 해당 유저가 팔로잉한 사람들을 검색
    Page<FollowerProjection> findAllByFollowerId(User user, Pageable pageable); // 해당 유저를 팔로워한 사람들을 검색
    Follow findByFolloweeIdAndFollowerId(User followee, User follower);
    Integer countAllByFollowerId(User follower); // 해당 유저를 팔로워한 사람들의 수를 반환
    Integer countAllByFolloweeId(User followee); // 해당 유저가 팔로잉한 사람들의 수를 반환
}
