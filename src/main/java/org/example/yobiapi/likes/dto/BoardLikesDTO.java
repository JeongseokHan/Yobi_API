package org.example.yobiapi.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikesDTO {
    private String userId;
    private Integer boardId;
}
