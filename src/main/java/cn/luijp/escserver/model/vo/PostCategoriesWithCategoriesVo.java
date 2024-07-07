package cn.luijp.escserver.model.vo;

import lombok.Data;

@Data
public class PostCategoriesWithCategoriesVo {
    private Integer postCategoriesId;
    private String postId;
    private String categoryId;
    private String categoryName;
    private String categoryAlias;
    private Integer categoryParentId;
}
