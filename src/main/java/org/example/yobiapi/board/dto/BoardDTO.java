package org.example.yobiapi.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private String title;
    private String content;
    private String category;
    private String userId;
}
