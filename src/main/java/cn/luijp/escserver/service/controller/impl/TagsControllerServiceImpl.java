package cn.luijp.escserver.service.controller.impl;


import cn.luijp.escserver.mapper.PostTagsMapper;
import cn.luijp.escserver.model.entity.PostTags;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import cn.luijp.escserver.service.controller.TagsControllerService;
import cn.luijp.escserver.service.db.IPostTagsService;
import cn.luijp.escserver.service.db.ITagsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TagsControllerServiceImpl implements TagsControllerService {

    private final ITagsService tagsService;

    private final IPostTagsService postTagsService;

    private final PostTagsMapper postTagsMapper;

    @Autowired
    public TagsControllerServiceImpl(ITagsService tagsService, IPostTagsService postTagsService, PostTagsMapper postTagsMapper) {
        this.tagsService = tagsService;
        this.postTagsService = postTagsService;
        this.postTagsMapper = postTagsMapper;
    }

    public List<Tags> getAllTags() {
        return tagsService.list();
    }

    public Boolean updateTag(Tags tag) {
        if (Objects.equals(tag.getName(), "")) {
            return delTag(tag);
        }
        try {
            return tagsService.saveOrUpdate(tag);
        } catch (Exception ex) {
            return false;
        }

    }

    public Boolean delTag(Tags tag) {
        return delTag(tag.getId());

    }

    public Boolean delTag(Integer tagId) {
        try {
            boolean status = tagsService.removeById(tagId);
            LambdaQueryWrapper<PostTags> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PostTags::getTagId, tagId);
            postTagsService.remove(queryWrapper);
            return status;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<PostTagsWithTagsVo> getTagsByPostId(Integer postId) {
        LambdaQueryWrapper<PostTags> postTagsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        postTagsLambdaQueryWrapper.eq(PostTags::getPostId, postId);
        return postTagsMapper.getPostTagsWithTags(postTagsLambdaQueryWrapper);
    }
}
