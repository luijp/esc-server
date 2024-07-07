package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;

import java.util.List;

public interface CategoriesControllerService {

    List<Categories> getAllCategories();

    Boolean updateCategory(Categories category);

    Boolean delCategory(Categories category);

    Boolean delCategory(Integer categoryId);

    List<PostCategoriesWithCategoriesVo> getCategoriesByPostId(Integer postId);
}
