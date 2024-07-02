package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.model.entity.CustomSettings;
import cn.luijp.escserver.mapper.CustomSettingsMapper;
import cn.luijp.escserver.service.ICustomSettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CustomSettingsServiceImpl extends ServiceImpl<CustomSettingsMapper, CustomSettings> implements ICustomSettingsService {

}
