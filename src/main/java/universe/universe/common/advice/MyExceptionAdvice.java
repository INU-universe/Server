import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import universe.universe.common.exception.*;
import universe.universe.common.reponse.Response;

@Slf4j
@RestControllerAdvice
public class MyExceptionAdvice {
    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        return buildResponse(e.status().value(), e.body(), e.getMessage());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e){
        return buildResponse(e.status().value(), e.body(), e.getMessage());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        return buildResponse(e.status().value(), e.body(), e.getMessage());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e){
        return buildResponse(e.status().value(), e.body(), e.getMessage());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e){
        return buildResponse(e.status().value(), e.body(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e){
        Response<String> response = Response.ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "unknownServerError", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildResponse(int code, Object data, String message) {
        Response<Object> response = Response.ERROR(code, message, data);
        return new ResponseEntity<>(response, HttpStatus.valueOf(code));
    }
}