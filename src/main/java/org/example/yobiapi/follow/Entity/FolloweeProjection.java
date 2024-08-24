package org.example.yobiapi.follow.Entity;

import org.example.yobiapi.user.Entity.UserProjection;

public interface FolloweeProjection {
    UserProjection getFollowerId();
}
