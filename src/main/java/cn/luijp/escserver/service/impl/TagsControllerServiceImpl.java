package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.service.ITagsService;
import cn.luijp.escserver.service.TagsControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class TagsControllerServiceImpl implements TagsControllerService {

    private final ITagsService tagsService;

    @Autowired
    public TagsControllerServiceImpl(ITagsService tagsService) {
        this.tagsService = tagsService;
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
        return tagsService.removeById(tag.getId());
    }

    public Boolean delTag(Integer tagId){
        return tagsService.removeById(tagId);

    }
}
