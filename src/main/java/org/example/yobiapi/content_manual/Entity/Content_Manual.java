package org.example.yobiapi.content_manual.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.content_manual.dto.Content_ManualDTO;
import org.example.yobiapi.recipe.Entity.Recipe;

@Entity
@Getter
@Setter
@Table(name = "CONTENT_MANUAL")
public class Content_Manual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manual_id")
    private Integer manualId;

    @Column(name = "content_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @Column(name = "content_id", nullable = false)
    private Integer contentId;

    @Column(name = "step_number")
    private Integer stepNumber;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "image", length = 200)
    private String image;

    @ManyToOne
    @JoinColumn(name = "board_id", insertable = false, updatable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    private Recipe recipe;

    @Builder
    public static Content_Manual toContent_Manual(Content_ManualDTO dto) {
        Content_Manual contentManual = new Content_Manual();
        contentManual.setManualId(dto.getManualId());
        contentManual.setContentType(dto.getContentType());
        contentManual.setContentId(dto.getContentId());
        contentManual.setStepNumber(dto.getStepNumber());
        contentManual.setDescription(dto.getDescription());
        contentManual.setImage(dto.getImage());
        return contentManual;
    }
}