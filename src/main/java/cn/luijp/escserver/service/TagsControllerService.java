package cn.luijp.escserver.service;

import cn.luijp.escserver.model.entity.Tags;

import java.util.List;

public interface TagsControllerService {

    List<Tags> getAllTags();

    Boolean updateTag(Tags tag);

    Boolean delTag(Tags tag);

    Boolean delTag(Integer tagId);

    List<Tags> getTagsByPostId(Integer postId);
}
