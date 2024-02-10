package universe.universe.common.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private Integer code;
    private String message;
    private ResponseStatus status;
    private T data;

    public Response(Integer code, ResponseStatus status, String message, T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T>Response<T> SUCCESS (Integer code, String message, T data) {
        return new Response(code, ResponseStatus.SUCCESS, message, data);
    }

    public static <T>Response<T> FAILURE (Integer code, String message, T data) {
        return new Response(code, ResponseStatus.FAIL, message, data);
    }

    public static <T>Response<T> ERROR (Integer code, String message, T data) {
        return new Response(code, ResponseStatus.ERROR, message, data);
    }
}