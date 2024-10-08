package org.example.yobiapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "사용자를 찾을 수 없습니다."),
    ID_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ACCOUNT-002", "이미 존재하는 ID 입니다."),
    ID_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "아이디를 15글자 이내로 설정해주세요."),
    PW_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-004", "비밀번호를 15글자 이내로 설정해주세요."),
    NICKNAME_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-005", "닉네임을 10글자 이내로 설정해주세요."),
    ID_SHORT_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-006", "아이디를 6글자 이상 적어주세요."),
    PW_SHORT_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-007", "비밀번호를 6글자 이상 적어주세요."),
    NICKNAME_SHORT_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-008", "닉네임을 2글자 이상 적어주세요."),
    NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ACCOUNT-009", "이미 존재하는 닉네임 입니다."),
    CATEGORY_IS_EMPTY(HttpStatus.BAD_REQUEST, "ACCOUNT-010", "카테고리를 입력해주세요."),
    CATEGORY_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-011", "카테고리는 45자 이내로 작성해주세요."),
    INGREDIENT_IS_EMPTY(HttpStatus.BAD_REQUEST, "ACCOUNT-012", "재료를 입력해주세요."),
    INGREDIENT_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-013", "재료는 300자 이내로 작성해주세요."),
    Title_IS_EMPTY(HttpStatus.BAD_REQUEST, "ACCOUNT-014", "제목을 입력해주세요."),
    Title_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-015", "제목은 45자 이내로 작성해주세요."),
    AUTHOR_NOT_EQUAL(HttpStatus.BAD_REQUEST, "ACCOUNT-016", "작성자가 일치하지 않습니다."),
    Recipe_NOT_FOUND(HttpStatus.BAD_REQUEST, "ACCOUNT-017", "레시피를 찾을 수 없습니다."),
    Content_Is_Empty(HttpStatus.BAD_REQUEST, "ACCOUNT-018", "내용을 적어주세요"),
    Board_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-019", "게시판이 존재하지 않습니다."),
    Bookmark_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-020", "북마크가 존재하지 않습니다."),
    Discription_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-021", "설명은 150자 이내로 작성해주세요."),
    CONTENT_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-022", "내용은 200자 이내로 작성해주세요."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-023", "댓글을 찾을 수 없습니다."),
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-024", "신고 내용을 찾을 수 없습니다."),
    REASON_IS_EMPTY(HttpStatus.BAD_REQUEST, "ACCOUNT-025", "신고 사유를 적어주세요."),
    REASON_LONG_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-026", "신고 사유는 150자 이내로 작성해주세요."),
    FOLLOW_BAD_REQUEST(HttpStatus.BAD_REQUEST, "ACCOUNT-027", "자신을 팔로우 하는 것은 불가능합니다.");


    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;				// ACCOUNT-001
    private final String message;			// 설명
}
