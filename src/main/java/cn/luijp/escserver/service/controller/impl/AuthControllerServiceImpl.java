package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.model.entity.Auth;
import cn.luijp.escserver.model.entity.Login;
import cn.luijp.escserver.model.entity.LoginFailed;
import cn.luijp.escserver.service.controller.AuthControllerService;
import cn.luijp.escserver.service.db.IAuthService;
import cn.luijp.escserver.service.db.ILoginFailedService;
import cn.luijp.escserver.service.db.ILoginService;
import cn.luijp.escserver.util.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthControllerServiceImpl implements AuthControllerService {

    private final JwtUtil jwtUtil;

    private final IAuthService authService;

    private final ILoginService loginService;

    private final ILoginFailedService loginFailedService;

    @Autowired
    public AuthControllerServiceImpl(JwtUtil jwtUtil, IAuthService authService, ILoginService loginService, ILoginFailedService loginFailedService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.loginService = loginService;
        this.loginFailedService = loginFailedService;
    }

    public Login login(String username, String password) {
        QueryWrapper<Auth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        Auth auth = authService.getOne(queryWrapper);
        if (auth == null) {
            return null;
        } else {
            String uuid = UUID.randomUUID().toString();
            String token = jwtUtil.generateToken(uuid);
            Login login = new Login();
            login.setUuid(uuid);
            login.setToken(token);
            login.setCreateTime(LocalDateTime.now());
            loginService.save(login);
            return login;
        }
    }

    public void logout(HttpServletRequest request) {
        Login login = auth(request);
        if (login != null) {
            UpdateWrapper<Login> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("uuid", login.getUuid()).set("token", "");
            loginService.update(updateWrapper);
        }
    }

    public Login auth(HttpServletRequest request) {
        Cookie[] browserCookies = request.getCookies();
        if (browserCookies != null) {
            for (Cookie cookie : browserCookies) {
                if (cookie.getName().equals("jwt")) {
                    String token = cookie.getValue();
                    DecodedJWT verify = jwtUtil.verify(token);
                    if (verify == null) {
                        return null;
                    }
                    LambdaQueryWrapper<Login> loginQueryWrapper = new LambdaQueryWrapper<>();
                    loginQueryWrapper.eq(Login::getToken, token);
                    return loginService.getOne(loginQueryWrapper);
                }
            }
        }
        return null;
    }

    public void recordFailed(String ip) {
        LoginFailed loginFailed = new LoginFailed();
        loginFailed.setIp(ip);
        loginFailedService.save(loginFailed);
    }

    public Boolean checkRate(String ip) {
        LocalDateTime oneDayBefore = LocalDateTime.now().minus(Duration.ofDays(1));
        LambdaQueryWrapper<LoginFailed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(LoginFailed::getCreateTime, oneDayBefore).eq(LoginFailed::getIp, ip);
        List<LoginFailed> loginFailedList = loginFailedService.list(queryWrapper);
        return loginFailedList.size() < 30;
    }
}
