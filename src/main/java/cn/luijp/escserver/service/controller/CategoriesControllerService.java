package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.dto.CategoriesAllDto;
import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;

import java.util.List;

public interface CategoriesControllerService {

    List<CategoriesAllDto> getAllCategories();

    Boolean updateCategory(Categories category);

    Boolean delCategory(Categories category);

    Boolean delCategory(Long categoryId);

    List<PostCategoriesWithCategoriesVo> getCategoriesByPostId(Long postId);

    public List<Categories> getCategoriesList();
}
