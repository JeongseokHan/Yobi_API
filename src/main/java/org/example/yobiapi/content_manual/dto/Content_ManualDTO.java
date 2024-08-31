package org.example.yobiapi.content_manual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.content_manual.Entity.ContentType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Content_ManualDTO {
    private Integer manualId;
    private ContentType contentType;
    private Integer contentId;
    private Integer stepNumber;
    private String description;
    private String image;
}
