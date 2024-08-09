package org.example.yobiapi.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardReportDTO {
    private Integer boardId;
    private String userId;
    private String reason;
}