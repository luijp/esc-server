package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.mapper.PostCategoriesMapper;
import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.PostCategories;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;
import cn.luijp.escserver.service.controller.CategoriesControllerService;
import cn.luijp.escserver.service.db.ICategoriesService;
import cn.luijp.escserver.service.db.IPostCategoriesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoriesControllerServiceImpl implements CategoriesControllerService {

    private final ICategoriesService categoriesService;

    private final IPostCategoriesService postCategoriesService;

    private final PostCategoriesMapper postCategoriesMapper;


    @Autowired
    public CategoriesControllerServiceImpl(ICategoriesService categoriesService,
                                           IPostCategoriesService postCategoriesService,
                                           PostCategoriesMapper postCategoriesMapper) {
        this.categoriesService = categoriesService;
        this.postCategoriesService = postCategoriesService;
        this.postCategoriesMapper = postCategoriesMapper;
    }

    public List<Categories> getAllCategories() {
        return categoriesService.list();
    }

    public Boolean updateCategory(Categories category) {
        if (Objects.equals(category.getName(), "")) {
            return delCategory(category);
        }
        try {
            return categoriesService.saveOrUpdate(category);
        } catch (Exception ex) {
            return false;
        }
    }

    public Boolean delCategory(Categories category) {
        return delCategory(category.getId());

    }

    public Boolean delCategory(Long categoryId) {
        try {
            boolean status = categoriesService.removeById(categoryId);
            LambdaQueryWrapper<PostCategories> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PostCategories::getCategoryId, categoryId);
            postCategoriesService.remove(wrapper);
            return status;

        } catch (Exception ex) {
            return false;
        }
    }

    public List<PostCategoriesWithCategoriesVo> getCategoriesByPostId(Long postId) {
        LambdaQueryWrapper<PostCategories> postCategoriesQueryWrapper = new LambdaQueryWrapper<>();
        postCategoriesQueryWrapper.eq(PostCategories::getPostId, postId);
        return postCategoriesMapper.getPostCategoriesWithCategories(postCategoriesQueryWrapper);

    }
}
