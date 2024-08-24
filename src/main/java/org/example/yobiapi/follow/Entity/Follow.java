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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "follower_id", referencedColumnName = "user_id")
    private User followerId; // 팔로우를 받은 사람

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "followee_id", referencedColumnName = "user_id")
    private User followeeId; // 팔로우를 하는 사람

    public static Follow toFollow(User follower, User followee) {
        Follow follow = new Follow();
        follow.setFollowerId(follower);
        follow.setFolloweeId(followee);
        return follow;
    }
}
