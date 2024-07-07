package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.model.entity.Auth;
import cn.luijp.escserver.model.entity.Login;
import cn.luijp.escserver.service.controller.AuthControllerService;
import cn.luijp.escserver.service.db.IAuthService;
import cn.luijp.escserver.service.db.ILoginService;
import cn.luijp.escserver.util.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthControllerServiceImpl implements AuthControllerService {

    private final JwtUtil jwtUtil;

    private final IAuthService authService;

    private final ILoginService loginService;

    @Autowired
    public AuthControllerServiceImpl(JwtUtil jwtUtil, IAuthService authService, ILoginService loginService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.loginService = loginService;
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
            login.setLoginTime(LocalDateTime.now());
            loginService.save(login);
            return login;
        }
    }

    public void logout(String token) {
        Login login = auth(token);
        if (login != null) {
            UpdateWrapper<Login> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("uuid", login.getUuid()).set("token", "");
            loginService.update(updateWrapper);
        }
    }

    public Login auth(String token) {
        DecodedJWT verify = jwtUtil.verify(token);
        if (verify == null) {
            return null;
        }
        LambdaQueryWrapper<Login> loginQueryWrapper = new LambdaQueryWrapper<>();
        loginQueryWrapper.eq(Login::getToken, token);
        return loginService.getOne(loginQueryWrapper);

    }
}
