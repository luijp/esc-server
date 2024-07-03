package cn.luijp.escserver.service;

import cn.luijp.escserver.model.entity.Login;

public interface AuthControllerService {

    public Login login(String username, String password);

    public void logout(String token);

    public Login auth(String token);

}
