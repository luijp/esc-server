package cn.luijp.escserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> ResponseDto<T> success() {
        return new ResponseDto<>(0, "", null);
    }

    public static <T> ResponseDto<T> success(String msg) {
        return new ResponseDto<>(0, msg, null);
    }

    public static <T> ResponseDto<T> successWithData(T data) {
        return new ResponseDto<>(0, "", data);
    }

    public static <T> ResponseDto<T> error(int code, String msg) {
        return new ResponseDto<>(code, msg, null);
    }
}
