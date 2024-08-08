package cn.luijp.escserver.controller;

import cn.luijp.escserver.Exception.TooManyRequestException;
import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Auth;
import cn.luijp.escserver.model.entity.Login;
import cn.luijp.escserver.service.controller.AuthControllerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthControllerService authControllerService;

    @Autowired
    public AuthController(AuthControllerService authControllerService) {
        this.authControllerService = authControllerService;
    }

    @PostMapping("/")
    public ResponseDto<Object> index(HttpServletResponse response,
                                     HttpServletRequest request) {
        Login auth = authControllerService.auth(request);
        if (auth != null) {
            return ResponseDto.success();
        }
        return ResponseDto.error(-1, "Jwt verify failed");
    }

    @PostMapping("/login")
    public ResponseDto<Object> login(@RequestBody Auth auth, HttpServletResponse response, HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        if(!authControllerService.checkRate(clientIp)){
            throw new TooManyRequestException();
        }
        Login login = authControllerService.login(auth.getUsername(), auth.getPassword());
        if (login == null) {
            authControllerService.recordFailed(clientIp);
            return ResponseDto.error(-1, "Login failed");
        }
        Cookie cookie = new Cookie("jwt", login.getToken());
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24 * 365);
        response.addCookie(cookie);
        return ResponseDto.success();
    }

    @PostMapping("/logout")
    public ResponseDto<Object> logout(HttpServletResponse response,
                                      HttpServletRequest request) {
        Cookie[] browserCookies = request.getCookies();
        String token = null;
        if (browserCookies != null) {
            for (Cookie cookie : browserCookies) {
                if (cookie.getName().equals("jwt")) {
                    token = cookie.getValue();
                }
                cookie.setMaxAge(0);
                cookie.setValue(null);
                response.addCookie(cookie);
            }
        }
        if (token != null) {
            authControllerService.logout(request);
        }
        return ResponseDto.success();
    }

}
