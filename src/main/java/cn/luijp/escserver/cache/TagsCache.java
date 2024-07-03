package cn.luijp.escserver.cache;

import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.service.ITagsService;
import cn.luijp.escserver.service.TagsControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TagsCache {

    @Autowired
    private TagsControllerService tagsControllerService;

    private static final Map<Integer, Tags> tagsList = new HashMap<>();

    public void fresh(){
        tagsList.clear();
        List<Tags> allTags = tagsControllerService.getAllTags();
        allTags.forEach(item -> {
            tagsList.put(item.getId(), item);
        });
    }

    public static Tags get(Integer id){
        if(tagsList.isEmpty()){
            TagsCache tc = new TagsCache();
            tc.fresh();
        }
        return tagsList.get(id);
    }
}
