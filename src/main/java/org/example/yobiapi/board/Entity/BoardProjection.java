package org.example.yobiapi.board.Entity;

import org.example.yobiapi.user.Entity.UserProjection;

import java.time.LocalDateTime;

public interface BoardProjection {
    Integer getBoardId();
    String getTitle();
    String getContent();
    UserProjection getUser();
    LocalDateTime getCreateDate();
    LocalDateTime getUpdatedDate();
    Integer getViews();
    String getCategory();
    Integer getReportCount();
    String getBoardThumbnail();
}
