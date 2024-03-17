package universe.universe.global.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.global.common.reponse.ApiResponse;
import universe.universe.global.common.reponse.ErrorCode;
import universe.universe.global.common.valid.Valid;

// 유효성 검사 실패, 잘못된 파라메터 요청
//@Getter
//public class CustomException extends RuntimeException {
//    private String key;
//    private String value;
//    private ErrorCode errorCode;
//
//    public CustomException(String key) {
//        this.key = key;
//    }
//    public CustomException(String key, String value) {
//        super(value);
//        this.key = key;
//        this.value = value;
//    }
//
//    public ApiResponse<?> body(){
//        Valid valid = new Valid(key, value);
//        return ApiResponse.FAILURE(HttpStatus.BAD_REQUEST.value(), "CustomException : BadRequest");
//    }
//
//    public HttpStatus status(){
//        return HttpStatus.BAD_REQUEST;
//    }
//}
@Getter
public class CustomException extends RuntimeException {
//    private String key;
    private String msg;
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public CustomException(ErrorCode errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }
//    public CustomException(String key, ErrorCode errorCode) {
//        super(errorCode.getMsg());
//        this.key = key;
//        this.errorCode = errorCode;
//    }

    public ApiResponse<?> body(){
//        Valid valid = new Valid(key, errorCode.getMsg());
        return ApiResponse.FAILURE(errorCode.getCode(), "CustomException : " + errorCode.getMsg());
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}