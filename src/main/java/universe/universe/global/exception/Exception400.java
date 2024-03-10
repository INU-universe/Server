package universe.universe.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.global.reponse.ApiResponse;
import universe.universe.global.valid.Valid;

// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class Exception400 extends RuntimeException {

    private String key;
    private String value;

    public Exception400(String key, String value) {
        super(value);
        this.key = key;
        this.value = value;
    }

    public ApiResponse<?> body(){
        Valid valid = new Valid(key, value);
        return ApiResponse.FAILURE(HttpStatus.BAD_REQUEST.value(), "badRequest");
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}