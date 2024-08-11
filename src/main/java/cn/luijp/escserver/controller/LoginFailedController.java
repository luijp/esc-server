package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.LoginFailed;
import cn.luijp.escserver.service.controller.LoginFailedControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/login_failed")
public class LoginFailedController {

    private final LoginFailedControllerService loginFailedControllerService;

    @Autowired
    public LoginFailedController(LoginFailedControllerService loginFailedControllerService) {
        this.loginFailedControllerService = loginFailedControllerService;
    }

    @PostMapping("/list")
    public ResponseDto<List<LoginFailed>> list() {
        return ResponseDto.successWithData(loginFailedControllerService.getLoginFailed());
    }
}
