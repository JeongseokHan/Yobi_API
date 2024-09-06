package org.example.yobiapi.user.Entity;

import org.example.yobiapi.user.dto.ResponseUserProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    User findByNickName(String nickName);
    User findByUserIdAndSocialType(String userId, String passWord);
}
