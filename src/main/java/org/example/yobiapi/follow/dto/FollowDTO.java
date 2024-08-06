package org.example.yobiapi.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowDTO {
    private String followerId;
    private String followeeId;
}
