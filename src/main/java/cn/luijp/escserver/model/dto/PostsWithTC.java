package cn.luijp.escserver.model.dto;

import cn.luijp.escserver.model.entity.Posts;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import lombok.Data;

import java.util.List;

@Data
public class PostsWithTC extends Posts {
    List<PostTagsWithTagsVo> tags;
    List<PostCategoriesWithCategoriesVo> categories;
}
