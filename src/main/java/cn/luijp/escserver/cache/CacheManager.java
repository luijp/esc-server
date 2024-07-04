package cn.luijp.escserver.cache;

import cn.luijp.escserver.service.CategoriesControllerService;
import cn.luijp.escserver.service.ICategoriesService;
import cn.luijp.escserver.service.ITagsService;
import cn.luijp.escserver.service.TagsControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheManager {

    private final ICategoriesService categoriesService;

    private final ITagsService tagsService;

    @Autowired
    public CacheManager(ICategoriesService categoriesService,
                        ITagsService tagsService) {
        this.categoriesService = categoriesService;
        this.tagsService = tagsService;

    }

    public void init() {
        //Category
        CategoriesCache.CategoriesMap.clear();
        categoriesService.list().forEach(item -> {
            CategoriesCache.CategoriesMap.put(item.getId(), item);
        });

        //Tags
        TagsCache.tagsMap.clear();
        tagsService.list().forEach(item -> {
            TagsCache.tagsMap.put(item.getId(), item);
        });
    }
}
