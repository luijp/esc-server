package cn.luijp.escserver.model.vo;

import lombok.Data;

@Data
public class PostTagsWithTagsVo {
    private Integer postTagsId;
    private String postId;
    private String tagId;
    private String tagName;
    private String tagAlias;
}
