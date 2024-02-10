package universe.universe.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.common.reponse.Response;

// 리소스를 찾을 수 없음
@Getter
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public Response<?> body(){
        return Response.FAILURE(HttpStatus.NOT_FOUND.value(), "notFound", null);
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}