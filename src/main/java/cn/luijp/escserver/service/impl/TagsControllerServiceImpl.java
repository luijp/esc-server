package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.PostTags;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.service.IPostTagsService;
import cn.luijp.escserver.service.ITagsService;
import cn.luijp.escserver.service.TagsControllerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class TagsControllerServiceImpl implements TagsControllerService {

    private final ITagsService tagsService;

    private final IPostTagsService postTagsService;

    @Autowired
    public TagsControllerServiceImpl(ITagsService tagsService, IPostTagsService postTagsService) {
        this.tagsService = tagsService;
        this.postTagsService = postTagsService;
    }

    public List<Tags> getAllTags(){
        return tagsService.list();
    }

    public Boolean updateTag(Tags tag) {
        if(Objects.equals(tag.getName(), "")){
            return delTag(tag);
        }
        try{
            return tagsService.saveOrUpdate(tag);
        }catch (Exception ex){
            return false;
        }

    }

    public Boolean delTag(Tags tag){
        return delTag(tag.getId());

    }

    public Boolean delTag(Integer tagId){
        try{
            tagsService.removeById(tagId);
            QueryWrapper<PostTags> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tag_id", tagId);
            postTagsService.remove(queryWrapper);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
