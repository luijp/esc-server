package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.mapper.PostCategoriesMapper;
import cn.luijp.escserver.model.dto.CategoriesAllDto;
import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.PostCategories;
import cn.luijp.escserver.model.entity.PostTags;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import cn.luijp.escserver.service.controller.CategoriesControllerService;
import cn.luijp.escserver.service.db.ICategoriesService;
import cn.luijp.escserver.service.db.IPostCategoriesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<CategoriesAllDto> getAllCategories() {
        List<Categories> list = categoriesService.list();
        return getCategories(list,0L);
    }

    public List<Categories> getCategoriesList() {
        return categoriesService.list();
    }

    public Categories getCategoryIdByAlias(String categoryAlias) {
        LambdaQueryWrapper<Categories> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Categories::getAlias,categoryAlias);
        return categoriesService.getOne(queryWrapper);
    }

    private List<CategoriesAllDto> getCategories(List<Categories> categoriesList, Long parentId) {
        List<CategoriesAllDto> categoriesAllDtoList = new ArrayList<>();
        categoriesList.forEach((item)->{
            if(Objects.equals(item.getParentId(), parentId)){
                CategoriesAllDto categoriesAllDto =new CategoriesAllDto();
                categoriesAllDto.setId(item.getId());
                categoriesAllDto.setName(item.getName());
                categoriesAllDto.setParentId(item.getParentId());
                categoriesAllDto.setAlias(item.getAlias());

                categoriesAllDtoList.add(categoriesAllDto);
                List<CategoriesAllDto> childCategories = getCategories(categoriesList,item.getId());
                categoriesAllDto.setChildren(childCategories);
            }
        });


        return categoriesAllDtoList;

    }

    public Long updateCategory(Categories category) {
        if (Objects.equals(category.getName(), "")) {
            delCategory(category);
            return 0L;
        }
        try {
            boolean status = categoriesService.saveOrUpdate(category);
            if(status){
                return category.getId();
            }
            return null;
        } catch (Exception ex) {
            return null;
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
        MPJLambdaWrapper<PostCategories> wrapper = JoinWrappers.lambda(PostCategories.class)
                .selectAs(PostCategories::getId, PostCategoriesWithCategoriesVo::getPostCategoriesId)
                .selectAs(PostCategories::getPostId,PostCategoriesWithCategoriesVo::getPostId)
                .selectAs(PostCategories::getCategoryId,PostCategoriesWithCategoriesVo::getCategoryId)
                .selectAs(Categories::getName,PostCategoriesWithCategoriesVo::getCategoryName)
                .selectAs(Categories::getAlias,PostCategoriesWithCategoriesVo::getCategoryAlias)
                .leftJoin(Categories.class, Categories::getId, PostCategories::getCategoryId)
                .eq(PostCategories::getPostId, postId);
        return postCategoriesMapper.selectJoinList(PostCategoriesWithCategoriesVo.class,wrapper);
    }
}
