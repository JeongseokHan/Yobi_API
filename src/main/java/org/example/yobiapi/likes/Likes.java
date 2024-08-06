package org.example.yobiapi.likes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.board.Entity.Board;
import org.example.yobiapi.user.Entity.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "like_id")
    private Integer likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "board_id", referencedColumnName = "board_id")
    private Board board;

    @CreationTimestamp
    @Column(nullable = false, name = "like_date")
    private LocalDateTime likeDate;

    public static Likes toLikes(LikesDTO likesDTO) {
        Likes likes = new Likes();
        likes.user.setUserId(likesDTO.getUserId());
        likes.board.setBoardId(likesDTO.getBoardId());

        return likes;
    }
}
