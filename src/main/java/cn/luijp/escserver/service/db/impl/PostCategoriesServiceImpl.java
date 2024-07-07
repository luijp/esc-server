package cn.luijp.escserver.service.db.impl;

import cn.luijp.escserver.mapper.PostCategoriesMapper;
import cn.luijp.escserver.model.entity.PostCategories;
import cn.luijp.escserver.service.db.IPostCategoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PostCategoriesServiceImpl extends ServiceImpl<PostCategoriesMapper, PostCategories> implements IPostCategoriesService {

}
