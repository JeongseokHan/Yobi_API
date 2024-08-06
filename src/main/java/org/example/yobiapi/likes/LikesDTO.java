package org.example.yobiapi.likes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikesDTO {
    private String userId;
    private Integer boardId;
}
