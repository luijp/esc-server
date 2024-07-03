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
    public static Map<Integer, Tags> tagsMap = new HashMap<>();

}
