package cn.luijp.escserver.mapper;

import cn.luijp.escserver.model.entity.PostCategories;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PostCategoriesMapper extends MPJBaseMapper<PostCategories> {
}
