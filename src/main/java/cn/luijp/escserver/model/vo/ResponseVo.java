package cn.luijp.escserver.model.vo;

import lombok.Data;

@Data
public class ResponseVo<T> {
    private int code;
    private String msg;
    private T data;

    public ResponseVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static<T> ResponseVo<T> success(){
        return new ResponseVo<T>(0, "", null);
    }

    public static<T> ResponseVo<T> success(String msg){
        return new ResponseVo<T>(0, msg, null);
    }

    public static<T> ResponseVo<T> error(String msg){
        return new ResponseVo<T>(-1, msg, null);
    }

    public static<T> ResponseVo<T> error(int code ,String msg){
        return new ResponseVo<T>(code, msg, null);
    }
}
