package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;

import java.util.List;

public interface TagsControllerService {

    List<Tags> getAllTags();

    Long updateTag(Tags tag);

    Boolean delTag(Tags tag);

    Boolean delTag(Long tagId);

    List<PostTagsWithTagsVo> getTagsByPostId(Long postId);

    Tags getTagIdByAlias(String tagAlias);
}
