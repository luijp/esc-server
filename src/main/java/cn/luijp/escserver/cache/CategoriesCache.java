package cn.luijp.escserver.cache;

import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.service.CategoriesControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoriesCache {

    @Autowired
    private CategoriesControllerService categoriesControllerService;

    private static final Map<Integer, Categories> CategoriesList = new HashMap<>();

    public void fresh(){
        CategoriesList.clear();
        List<Categories> allCategories = categoriesControllerService.getAllCategories();
        allCategories.forEach(item -> {
            CategoriesList.put(item.getId(), item);
        });
    }

    public static Categories get(Integer id){
        if(CategoriesList.isEmpty()){
            CategoriesCache cc = new CategoriesCache();
            cc.fresh();
        }
        return CategoriesList.get(id);
    }
}
