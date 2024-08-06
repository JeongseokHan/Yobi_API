package org.example.yobiapi.follow.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.follow.dto.FollowDTO;
import org.example.yobiapi.user.Entity.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "follow_id")
    private Integer followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "follower_id", referencedColumnName = "user_id")
    private User followerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "followee_id", referencedColumnName = "user_id")
    private User followeeId;

    public static Follow toFollow(FollowDTO followDTO) {
        Follow follow = new Follow();
        follow.followerId.setUserId(followDTO.getFollowerId());
        follow.followeeId.setUserId(followDTO.getFolloweeId());

        return follow;
    }
}
