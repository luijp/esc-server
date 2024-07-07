package cn.luijp.escserver.service.db.impl;

import cn.luijp.escserver.mapper.TagsMapper;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.service.db.ITagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

}
