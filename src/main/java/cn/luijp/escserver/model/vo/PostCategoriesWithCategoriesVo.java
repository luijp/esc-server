package cn.luijp.escserver.model.vo;

import lombok.Data;

@Data
public class PostCategoriesWithCategoriesVo {
    private Long postCategoriesId;
    private Long postId;
    private Long categoryId;
    private String categoryName;
    private String categoryAlias;
    private Long categoryParentId;
}
