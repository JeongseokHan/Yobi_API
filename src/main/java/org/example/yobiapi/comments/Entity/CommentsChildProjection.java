package org.example.yobiapi.comments.Entity;

import org.example.yobiapi.board.Entity.BoardIdProjection;
import org.example.yobiapi.user.Entity.UserProjection;

import java.time.LocalDateTime;

public interface CommentsChildProjection {
    Integer getCommentId();
    UserProjection getUser();
    String getContent();
    LocalDateTime getCreateContent();
    LocalDateTime getUpdateContent();
    Integer getReportCount();
    Integer getParentCommentId();
}
