package cn.luijp.escserver.service.controller.impl;


import cn.luijp.escserver.mapper.PostTagsMapper;
import cn.luijp.escserver.model.entity.PostTags;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import cn.luijp.escserver.service.controller.TagsControllerService;
import cn.luijp.escserver.service.db.IPostTagsService;
import cn.luijp.escserver.service.db.ITagsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
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

    public Long updateTag(Tags tag) {
        if (Objects.equals(tag.getName(), "")) {
            delTag(tag);
            return 0L;
        }
        try {
            boolean status = tagsService.saveOrUpdate(tag);
            if (status) {
                return tag.getId();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }

    }

    public Boolean delTag(Tags tag) {
        return delTag(tag.getId());

    }

    public Boolean delTag(Long tagId) {
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

    public List<PostTagsWithTagsVo> getTagsByPostId(Long postId) {
        MPJLambdaWrapper<PostTags> wrapper = JoinWrappers.lambda(PostTags.class)
                .selectAs(PostTags::getId, PostTagsWithTagsVo::getPostTagsId)
                .selectAs(PostTags::getPostId, PostTagsWithTagsVo::getPostId)
                .selectAs(PostTags::getTagId, PostTagsWithTagsVo::getTagId)
                .selectAs(Tags::getName, PostTagsWithTagsVo::getTagName)
                .selectAs(Tags::getAlias, PostTagsWithTagsVo::getTagAlias)
                .leftJoin(Tags.class, Tags::getId, PostTags::getTagId)
                .eq(PostTags::getPostId, postId);
        return postTagsMapper.selectJoinList(PostTagsWithTagsVo.class, wrapper);
    }

    public Tags getTagIdByAlias(String tagAlias) {
        LambdaQueryWrapper<Tags> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tags::getAlias, tagAlias);
        return tagsService.getOne(wrapper);
    }
}
