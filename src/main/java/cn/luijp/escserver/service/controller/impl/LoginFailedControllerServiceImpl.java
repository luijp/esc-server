package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.model.entity.LoginFailed;
import cn.luijp.escserver.service.controller.LoginFailedControllerService;
import cn.luijp.escserver.service.db.ILoginFailedService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginFailedControllerServiceImpl implements LoginFailedControllerService {

    private final ILoginFailedService loginFailedService;

    @Autowired
    public LoginFailedControllerServiceImpl(ILoginFailedService loginFailedService) {
        this.loginFailedService = loginFailedService;
    }

    public List<LoginFailed> getLoginFailed() {
        LambdaQueryWrapper<LoginFailed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(LoginFailed::getId);
        IPage<LoginFailed> loginFailedPage = loginFailedService.page(new Page<>(1, 10), queryWrapper);
        List<LoginFailed> records = loginFailedPage.getRecords();
        if (records.isEmpty()) {
            return null;
        }
        return records;
    }
}
