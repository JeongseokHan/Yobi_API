package org.example.yobiapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseUserProfileDTO {
    private String name;
    private Integer followersCount;
    private Integer followingCount;
    private String userProfile;
    private Integer boardCount;
    private Integer recipeCount;
}
