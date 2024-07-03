package cn.luijp.escserver.cache;

import cn.luijp.escserver.model.entity.Tags;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TagsCache {
    public static Map<Integer, Tags> tagsMap = new HashMap<>();

}
