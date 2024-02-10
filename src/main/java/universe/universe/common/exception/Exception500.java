package universe.universe.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.common.reponse.Response;

// 서버 에러
@Getter
public class Exception500 extends RuntimeException {
    public Exception500(String message) {
        super(message);
    }

    public Response<?> body(){
        return Response.ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "serverError", null);
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}