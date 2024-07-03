package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
    @RequestMapping("/")
    public ResponseDto<Object> index() {
        return ResponseDto.success("This is a API server");
    }
}
