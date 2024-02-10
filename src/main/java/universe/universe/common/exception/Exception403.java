package universe.universe.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.common.reponse.Response;

// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public Response<?> body(){
        return Response.FAILURE(HttpStatus.FORBIDDEN.value(), "forbidden", null);
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}
