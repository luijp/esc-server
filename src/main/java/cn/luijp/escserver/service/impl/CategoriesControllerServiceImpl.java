package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.cache.CacheManager;
import cn.luijp.escserver.cache.CategoriesCache;
import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.PostCategories;
import cn.luijp.escserver.service.CategoriesControllerService;
import cn.luijp.escserver.service.ICategoriesService;
import cn.luijp.escserver.service.IPostCategoriesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CategoriesControllerServiceImpl implements CategoriesControllerService {

    private final ICategoriesService categoriesService;

    private final IPostCategoriesService postCategoriesService;

    private final CacheManager cacheManager;

    @Autowired
    public CategoriesControllerServiceImpl(ICategoriesService categoriesService,
                                           IPostCategoriesService postCategoriesService,
                                           CacheManager cacheManager) {
        this.categoriesService = categoriesService;
        this.postCategoriesService = postCategoriesService;
        this.cacheManager = cacheManager;
    }

    public List<Categories> getAllCategories() {
        return categoriesService.list();
    }

    public Boolean updateCategory(Categories category) {
        if (Objects.equals(category.getName(), "")) {
            return delCategory(category);
        }
        try {
            boolean status = categoriesService.saveOrUpdate(category);
            cacheManager.init();
            return status;
        } catch (Exception ex) {
            return false;
        }
    }

    public Boolean delCategory(Categories category) {
        return delCategory(category.getId());

    }

    public Boolean delCategory(Integer categoryId) {
        try {
            boolean status = categoriesService.removeById(categoryId);
            QueryWrapper<PostCategories> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id", categoryId);
            postCategoriesService.remove(wrapper);
            cacheManager.init();
            return status;

        } catch (Exception ex) {
            return false;
        }


    }

    public List<Categories> getCategoriesByPostId(Integer postId) {
        QueryWrapper<PostCategories> postCategoriesQueryWrapper = new QueryWrapper<>();
        postCategoriesQueryWrapper.eq("post_id", postId);
        List<PostCategories> list = postCategoriesService.list(postCategoriesQueryWrapper);
        List<Categories> categories = new ArrayList<>();
        list.forEach(item -> {
            categories.add(CategoriesCache.CategoriesMap.get(item.getCategoryId()));
        });
        return categories;
    }
}
