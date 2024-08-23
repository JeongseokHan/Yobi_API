package org.example.yobiapi.board.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.yobiapi.board.dto.BoardDTO;
import org.example.yobiapi.user.Entity.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "board_id")
    private Integer boardId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @CreationTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ColumnDefault("0")
    @Column(name = "views")
    private Integer views;

    @Column(name = "category")
    private String category;

    @ColumnDefault("0")
    @Column(name = "report_count")
    private Integer reportCount;

    @Column(name = "board_thumbnail")
    private String boardThumbnail;

    @Builder
    public static Board toBoard(BoardDTO boardDTO, User user) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setCategory(boardDTO.getCategory());
        board.setContent(boardDTO.getContent());
        board.setUser(user);
        board.setBoardThumbnail(boardDTO.getBoardThumbnail());

        return board;
    }
}
