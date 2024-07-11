package cn.luijp.escserver.mapper;

import cn.luijp.escserver.model.entity.PostTags;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PostTagsMapper extends MPJBaseMapper<PostTags> {
}
