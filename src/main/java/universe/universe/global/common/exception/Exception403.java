package universe.universe.global.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.global.common.reponse.ApiResponse;

// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ApiResponse<?> body(){
        return ApiResponse.FAILURE(HttpStatus.FORBIDDEN.value(), "Exception403 : Forbidden");
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}
