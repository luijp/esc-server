package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.model.entity.Login;
import cn.luijp.escserver.mapper.LoginMapper;
import cn.luijp.escserver.service.ILoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

}
