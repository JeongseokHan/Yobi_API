package org.example.yobiapi.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardCommentsDTO {
    private Integer boardId;
    private String userId;
    private String content;
    private Integer contentId;
}
