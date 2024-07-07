package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.entity.Login;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthControllerService {

    Login login(String username, String password);

    void logout(HttpServletRequest request);

    Login auth(HttpServletRequest request);

}
