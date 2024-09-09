package org.example.yobiapi.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.content_manual.dto.Content_ManualDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Integer boardId;
    private String userId;
    private String title;
    private String content;
    private String category;
    private Integer views;
    private Integer reportCount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String boardThumbnail;
    private List<Content_ManualDTO> manuals;
}
