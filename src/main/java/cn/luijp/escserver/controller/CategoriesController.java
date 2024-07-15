package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.CategoriesAllDto;
import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;
import cn.luijp.escserver.service.controller.CategoriesControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    public final CategoriesControllerService categoriesControllerService;

    @Autowired
    public CategoriesController(CategoriesControllerService categoriesControllerService) {
        this.categoriesControllerService = categoriesControllerService;
    }

    @GetMapping("/")
    public ResponseDto<List<CategoriesAllDto>> getAllCategories() {
        return ResponseDto.successWithData(categoriesControllerService.getAllCategories());
    }

    @GetMapping("/list")
    public ResponseDto<List<Categories>> getCategoriesList() {
        return ResponseDto.successWithData(categoriesControllerService.getCategoriesList());
    }

    @GetMapping("/alias/{categoryAlias}")
    public ResponseDto<Categories> getCategoriesByTagAlias(@PathVariable String categoryAlias) {
        Categories categoryIdByAlias = categoriesControllerService.getCategoryIdByAlias(categoryAlias);
        if(categoryIdByAlias == null){
            return ResponseDto.error(-404, "Category not exist");
        }
        return ResponseDto.successWithData(categoryIdByAlias);
    }

    @PostMapping("/update")
    public ResponseDto<Long> updateCategory(@RequestBody Categories categories) {
        Long id = categoriesControllerService.updateCategory(categories);
        if (id != null) {
            return ResponseDto.successWithData(id);
        } else {
            return ResponseDto.error(-1, "Category update failed");
        }
    }

    @GetMapping("/get/{post_id}")
    public ResponseDto<List<PostCategoriesWithCategoriesVo>> getCategoriesByPostId(@PathVariable Long post_id) {
        return ResponseDto.successWithData(categoriesControllerService.getCategoriesByPostId(post_id));
    }

    @PostMapping("/del")
    public ResponseDto<Boolean> delCategory(@RequestBody Categories categories) {
        Boolean status = categoriesControllerService.delCategory(categories);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Category delete failed");
        }
    }

    @PostMapping("/del/{id}")
    public ResponseDto<Boolean> delCategoryById(@PathVariable Long id) {
        Boolean status = categoriesControllerService.delCategory(id);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Category delete failed");
        }
    }
}
