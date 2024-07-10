package cn.luijp.escserver.service.db.impl;

import cn.luijp.escserver.mapper.LoginFailedMapper;
import cn.luijp.escserver.model.entity.LoginFailed;
import cn.luijp.escserver.service.db.ILoginFailedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LoginFailedServiceImpl extends ServiceImpl<LoginFailedMapper, LoginFailed> implements ILoginFailedService {

}
