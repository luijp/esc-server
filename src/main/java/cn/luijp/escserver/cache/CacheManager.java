package cn.luijp.escserver.cache;

import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.service.CategoriesControllerService;
import cn.luijp.escserver.service.TagsControllerService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheManager {

    private final CategoriesControllerService categoriesControllerService;

    private final TagsControllerService tagsControllerService;

    @Autowired
    public CacheManager(CategoriesControllerService categoriesControllerService,TagsControllerService tagsControllerService) {
        this.categoriesControllerService = categoriesControllerService;
        this.tagsControllerService = tagsControllerService;
    }

    public void init() {
        //Category
        CategoriesCache.CategoriesMap.clear();
        categoriesControllerService.getAllCategories().forEach(item -> {
            CategoriesCache.CategoriesMap.put(item.getId(), item);
        });

        //Tags
        TagsCache.tagsMap.clear();
        tagsControllerService.getAllTags().forEach(item -> {
            TagsCache.tagsMap.put(item.getId(), item);
        });
    }
}
