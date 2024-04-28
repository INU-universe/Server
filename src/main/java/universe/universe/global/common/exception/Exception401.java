package universe.universe.global.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.global.common.reponse.ApiResponse;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
//    public Exception401(String message) {
//        super(message);
//    }
//
//    public ApiResponse<?> body(){
//        return ApiResponse.FAILURE(HttpStatus.UNAUTHORIZED.value(), "Exception401 : UnAuthorized");
//    }
//
//    public HttpStatus status(){
//        return HttpStatus.UNAUTHORIZED;
//    }
}
