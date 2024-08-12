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

    @Column(nullable = false, name = "password")
    private String passWord;

    @Column(nullable = false, name = "nickname")
    private String nickName;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @CreationTimestamp
    @Column(nullable = false, name = "join_date")
    private LocalDateTime joinDate;

    @ColumnDefault(("0"))
    @Column(name = "followers_count")
    private Integer followersCount;

    @ColumnDefault("0")
    @Column(name = "following_count")
    private Integer followingCount;

    @Builder
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setPassWord(userDTO.getPassWord());
        user.setNickName(userDTO.getNickName());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        return user;
    }

}
