package universe.universe.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.common.reponse.Response;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public Response<?> body(){
        return Response.FAILURE(HttpStatus.UNAUTHORIZED.value(), "unAuthorized", null);
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}
