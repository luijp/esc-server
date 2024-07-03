package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.service.CategoriesControllerService;
import cn.luijp.escserver.service.ICategoriesService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoriesControllerServiceImpl implements CategoriesControllerService {

    private final ICategoriesService categoriesService;

    @Autowired
    public CategoriesControllerServiceImpl(ICategoriesService categoriesService) {
        this.categoriesService = categoriesService;
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
        try{
            return categoriesService.removeById(category.getId());
        }catch (Exception ex){
            return false;
        }

    }

    public Boolean delCategory(Integer categoryId) {
        try{
            return categoriesService.removeById(categoryId);
        }catch (Exception ex){
            return false;
        }


    }
}
