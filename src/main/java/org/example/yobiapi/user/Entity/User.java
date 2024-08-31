package org.example.yobiapi.user.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.yobiapi.user.dto.UserDTO;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @Column(nullable = false, name = "user_id")
    private String userId;

    @Column(nullable = false, name = "social_type")
    private String socialType;

    @Column(name = "password")
    private String passWord;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @ColumnDefault(("0"))
    @Column(name = "followers_count")
    private Integer followersCount;

    @ColumnDefault("0")
    @Column(name = "following_count")
    private Integer followingCount;

    @Column(name = "user_profile")
    private String userProfile;

    @Builder
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getSocialType());
        user.setUserId(userDTO.getUserId());
        user.setPassWord(userDTO.getPassWord());
        user.setNickName(userDTO.getNickName());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }

}
