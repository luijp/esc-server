package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.mapper.CategoriesMapper;
import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.service.ICategoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories> implements ICategoriesService {

}
