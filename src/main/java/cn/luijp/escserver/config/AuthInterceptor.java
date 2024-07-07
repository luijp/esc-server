package cn.luijp.escserver.config;

import cn.luijp.escserver.Exception.AuthForbiddenException;
import cn.luijp.escserver.model.entity.Login;
import cn.luijp.escserver.service.controller.AuthControllerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthControllerService authControllerService;

    @Autowired
    public AuthInterceptor(AuthControllerService authControllerService) {
        this.authControllerService = authControllerService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("GET")){
            return true;
        }
        Login auth = authControllerService.auth(request);
        if(auth == null) {
            throw new AuthForbiddenException();
        }
        return true;
    }
}
