package org.example.yobiapi.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardChildCommentsDTO {
    Integer commentId;
    Integer boardId;
}