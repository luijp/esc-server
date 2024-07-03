package cn.luijp.escserver.service;

import cn.luijp.escserver.model.entity.Categories;

import java.util.List;

public interface CategoriesControllerService {

    List<Categories> getAllCategories();

    Boolean updateCategory(Categories category);

    Boolean delCategory(Categories category);

    Boolean delCategory(Integer categoryId);

    List<Categories> getCategoriesByPostId(Integer postId);
}
