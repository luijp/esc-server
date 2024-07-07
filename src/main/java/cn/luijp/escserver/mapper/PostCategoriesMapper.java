package cn.luijp.escserver.mapper;

import cn.luijp.escserver.model.entity.PostCategories;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PostCategoriesMapper extends BaseMapper<PostCategories> {

    @Select("SELECT post_categories.id AS postCategoriesId, " +
            "post_categories.post_id AS postId," +
            "post_categories.category_id AS categoryId," +
            "categories.name AS categoryName," +
            "categories.alias AS categoryAlias," +
            "categories.parent_id AS categoryParentId " +
            "FROM post_categories LEFT JOIN categories " +
            "ON post_categories.category_id = categories.id")
    List<PostCategoriesWithCategoriesVo> getPostCategoriesWithCategories(@Param(Constants.WRAPPER) Wrapper<PostCategories> wrapper);
}
