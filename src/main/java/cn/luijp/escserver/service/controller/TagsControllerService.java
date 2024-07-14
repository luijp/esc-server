package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TagsControllerService {

    List<Tags> getAllTags();

    Long updateTag(Tags tag);

    Boolean delTag(Tags tag);

    Boolean delTag(Long tagId);

    List<PostTagsWithTagsVo> getTagsByPostId(Long postId);

    public Tags getTagIdByAlias(String tagAlias);
}
