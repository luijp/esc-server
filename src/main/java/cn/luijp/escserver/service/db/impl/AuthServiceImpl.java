package cn.luijp.escserver.service.db.impl;

import cn.luijp.escserver.mapper.AuthMapper;
import cn.luijp.escserver.model.entity.Auth;
import cn.luijp.escserver.service.db.IAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements IAuthService {

}
