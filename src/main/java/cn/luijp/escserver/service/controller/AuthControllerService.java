package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.entity.Login;

public interface AuthControllerService {

    Login login(String username, String password);

    void logout(String token);

    Login auth(String token);

}
