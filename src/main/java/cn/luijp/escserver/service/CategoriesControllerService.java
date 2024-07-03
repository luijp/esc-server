package cn.luijp.escserver.service;

import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.Tags;

import java.util.List;

public interface CategoriesControllerService {

    public List<Categories> getAllCategories();

    public Boolean updateCategory(Categories category);

    public Boolean delCategory(Categories category);

    public Boolean delCategory(Integer categoryId);

    public List<Categories> getCategoriesByPostId(Integer postId);
}
