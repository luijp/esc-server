package cn.luijp.escserver.service.db.impl;

import cn.luijp.escserver.mapper.PostTagsMapper;
import cn.luijp.escserver.model.entity.PostTags;
import cn.luijp.escserver.service.db.IPostTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PostTagsServiceImpl extends ServiceImpl<PostTagsMapper, PostTags> implements IPostTagsService {

}
