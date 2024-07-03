package cn.luijp.escserver.Exception;

import cn.luijp.escserver.model.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseDto<Object> PostNotFoundException(Exception ex) {
        return ResponseDto.error(-404, "post not found");
    }

    @ExceptionHandler(Exception.class)
    public ResponseDto<Object> globalException(Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseDto.error(-1, "unknown exception");
    }
}
