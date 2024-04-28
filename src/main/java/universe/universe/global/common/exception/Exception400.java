package universe.universe.global.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import universe.universe.global.common.reponse.ApiResponse;
import universe.universe.global.common.valid.Valid;

@Getter
public class Exception400 extends RuntimeException {

//    private String key;
//    private String value;
//
//    public Exception400(String key, String value) {
//        super(value);
//        this.key = key;
//        this.value = value;
//    }
//
//    public ApiResponse<?> body(){
//        Valid valid = new Valid(key, value);
//        return ApiResponse.FAILURE(HttpStatus.BAD_REQUEST.value(), "Exception400: badRequest");
//    }
//
//    public HttpStatus status(){
//        return HttpStatus.BAD_REQUEST;
//    }
}
