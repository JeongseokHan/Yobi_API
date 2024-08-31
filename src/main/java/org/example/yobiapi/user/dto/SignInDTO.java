package org.example.yobiapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInDTO {
    private String userId;
    private String socialType;
}
