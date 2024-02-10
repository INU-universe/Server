package universe.universe.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.common.reponse.Response;
import universe.universe.common.valid.Valid;
import universe.universe.dto.valid.ValidDTO;

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

    public Response<?> body(){
        Valid valid = new Valid(key, value);
        return Response.FAILURE(HttpStatus.BAD_REQUEST.value(), "badRequest", valid);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}