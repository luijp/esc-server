package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.PostCategories;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.service.CategoriesControllerService;
import cn.luijp.escserver.service.ICategoriesService;
import cn.luijp.escserver.service.IPostCategoriesService;
import cn.luijp.escserver.service.IPostTagsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoriesControllerServiceImpl implements CategoriesControllerService {

    private final ICategoriesService categoriesService;

    private final IPostCategoriesService postCategoriesService;

    @Autowired
    public CategoriesControllerServiceImpl(ICategoriesService categoriesService,IPostCategoriesService postCategoriesService) {
        this.categoriesService = categoriesService;
        this.postCategoriesService = postCategoriesService;
    }

    public List<Categories> getAllCategories() {
        return categoriesService.list();
    }

    public Boolean updateCategory(Categories category) {
        if(Objects.equals(category.getName(), "")){
            return delCategory(category);
        }
        try{
            return categoriesService.saveOrUpdate(category);
        }catch (Exception ex){
            return false;
        }
    }

    public Boolean delCategory(Categories category) {
        return delCategory(category.getId());

    }

    public Boolean delCategory(Integer categoryId) {
        try{
            categoriesService.removeById(categoryId);
            QueryWrapper<PostCategories> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id", categoryId);
            postCategoriesService.remove(wrapper);
            return true;

        }catch (Exception ex){
            return false;
        }


    }
}
