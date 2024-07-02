package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.model.entity.Posts;
import cn.luijp.escserver.mapper.PostsMapper;
import cn.luijp.escserver.service.IPostsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

}
