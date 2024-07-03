package cn.luijp.escserver.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostsListDto {
    private List<PostsWithTC> postsList;
    private Long total;
    private Integer page;
    private Integer size;
}
