package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.cache.JwtCache;
import cn.luijp.escserver.model.entity.Auth;
import cn.luijp.escserver.model.entity.Login;
import cn.luijp.escserver.service.AuthControllerService;
import cn.luijp.escserver.service.IAuthService;
import cn.luijp.escserver.service.ILoginService;
import cn.luijp.escserver.util.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthControllerServiceImpl implements AuthControllerService {

    private final JwtUtil jwtUtil;

    private final IAuthService authService;

    private final ILoginService loginService;

    @Autowired
    public AuthControllerServiceImpl(JwtUtil jwtUtil,
                                     IAuthService authService,
                                     ILoginService loginService) {
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
            Map<String, String> map = new HashMap<>();
            map.put("uuid", uuid);
            String token = jwtUtil.generateToken(map);
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
            QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", login.getUuid());
            login.setToken("");
            JwtCache.jwtList.remove(login.getToken());
            loginService.update(queryWrapper);
        }
    }

    public Login auth(String token) {
        DecodedJWT verify = jwtUtil.verify(token);
        if (verify == null) {
            return null;
        }
        Login login = new Login();
        if (JwtCache.jwtList.contains(token)) {
            login.setUuid(verify.getClaim("uuid").asString());
            login.setLoginTime(LocalDateTime.now());
        } else {
            QueryWrapper<Login> loginQueryWrapper = new QueryWrapper<>();
            loginQueryWrapper.eq("token", token);
            login = loginService.getOne(loginQueryWrapper);
            if (login == null) {
                return null;
            } else {
                JwtCache.jwtList.add(token);
            }
        }
        return login;
    }
}
