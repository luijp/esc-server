package cn.luijp.escserver.service.db.impl;

import cn.luijp.escserver.mapper.AttachMapper;
import cn.luijp.escserver.model.entity.Attach;
import cn.luijp.escserver.service.db.IAttachService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AttachServiceImpl extends ServiceImpl<AttachMapper, Attach> implements IAttachService {
}
