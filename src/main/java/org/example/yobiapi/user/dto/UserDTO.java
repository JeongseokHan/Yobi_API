package org.example.yobiapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String socialType;
    private String passWord;
    private String nickName;
    private String name;
    private String email;
    private String phoneNumber;
}
