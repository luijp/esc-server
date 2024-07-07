package cn.luijp.escserver.mapper;

import cn.luijp.escserver.model.entity.PostTags;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PostTagsMapper extends BaseMapper<PostTags> {

    @Select("SELECT post_tags.id AS postTagsId, " +
            "post_tags.post_id AS postId," +
            "post_tags.tag_id AS tagId," +
            "tags.name AS tagName," +
            "tags.alias AS tagAlias " +
            "FROM post_tags LEFT JOIN tags " +
            "ON post_tags.tag_id = tags.id")
    List<PostTagsWithTagsVo> getPostTagsWithTags(@Param(Constants.WRAPPER) Wrapper<PostTags> wrapper);

}
