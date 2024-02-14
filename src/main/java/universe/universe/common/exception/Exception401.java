package universe.universe.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.common.reponse.ApiResponse;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ApiResponse<?> body(){
        return ApiResponse.FAILURE(HttpStatus.UNAUTHORIZED.value(), "unAuthorized");
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}
