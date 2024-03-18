package universe.universe.global.common.reponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /** Exception40x **/
    INVALID_ACCESS_TOKEN(401, "에세스 토큰이 유효하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(401, "에세스 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(401, "리프레쉬 토큰이 유효하지 않습니다."),
    EXPIRED_REFRESH_TOKEN(401, "리프레쉬 토큰이 만료되었습니다."),

    ACCESS_DENIED(403, "접근 권한이 없습니다."),
    BLACKLIST_TOKEN(403, "블랙 리스트에 존재하는 토큰입니다."),

    USER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    FRIEND_NOT_FOUND(404, "존재하지 않는 친구입니다."),
    CHATROOM_NOT_FOUND(404, "존재하지 않는 채팅방입니다."),
    CHATROOM_RELATION_NOT_FOUND(404, "존재하지 않는 채팅 관계입니다."),
    LOCATION_NOT_FOUND(404, "존재하지 않는 위치입니다."),
    MESSAGE_NOT_FOUND(404, "존재하지 않는 메세지입니다."),
    REFRESH_NOT_FOUND(404, "존재하지 않는 RefreshToken입니다."),

    USER_EXIST(409, "이미 가입된 이메일입니다."),
    FRIEND_EXIST(409, "이미 친구입니다."),

    CHAT_UNAVAILABLE(400, "본인에게 채팅은 불가합니다."),
    FRIEND_UNAVAILABLE(400, "본인에게 친구 추가 불가합니다."),
    AUTHENTICATION_FAILED(401, "아이디 또는 비밀번호가 옳지 않습니다."),

    /** Exception500 **/
    SERVER_ERROR(500, "서버 에러입니다.");

    /** Success **/

    private final int code;
    private final String msg;
}
