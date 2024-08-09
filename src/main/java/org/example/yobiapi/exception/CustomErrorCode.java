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
    NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ACCOUNT-009", "이미 존재하는 닉네임 입니다.");


    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;				// ACCOUNT-001
    private final String message;			// 설명
}
