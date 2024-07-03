package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.mapper.GlobalSettingsMapper;
import cn.luijp.escserver.model.entity.GlobalSettings;
import cn.luijp.escserver.service.IGlobalSettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GlobalSettingsServiceImpl extends ServiceImpl<GlobalSettingsMapper, GlobalSettings> implements IGlobalSettingsService {

}
