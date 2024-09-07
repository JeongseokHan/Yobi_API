package org.example.yobiapi.view_log.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.recipe.Entity.Recipe;
import org.example.yobiapi.user.Entity.User;
import org.example.yobiapi.view_log.dto.View_LogDTO;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "view_log")
public class View_Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "log_id")
    private Integer logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "recipe_id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id")
    private Board board;

    @CreationTimestamp
    @Column(name = "view_date")
    private LocalDateTime viewDate;

    public static View_Log toView_Log(View_LogDTO viewlogDTO) {
        View_Log viewLog = new View_Log();
        viewLog.user.setUserId(viewlogDTO.getUserId());
        viewLog.recipe.setRecipeId(viewlogDTO.getRecipeId());

        return viewLog;
    }
}
