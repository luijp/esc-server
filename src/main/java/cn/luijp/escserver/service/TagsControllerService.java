package cn.luijp.escserver.service;

import cn.luijp.escserver.model.entity.Tags;

import java.util.List;

public interface TagsControllerService {

    public List<Tags> getAllTags();

    public Boolean updateTag(Tags tag);

    public Boolean delTags(Tags tag);

    public Boolean delTags(Integer tagId);
}
