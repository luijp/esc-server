package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.dto.PostsListDto;
import cn.luijp.escserver.model.entity.Posts;

import java.util.List;

public interface PostsControllerService {

    Boolean updatePost(Posts posts);

    Boolean delPost(Long id);

    Posts getPost(Long id);

    PostsListDto getPostsList(Integer pageNum, Integer pageSize, Integer type);

    Boolean addTags(Long postId, List<Long> tagIds);

    Boolean addCategories(Long postId, List<Long> categoryIds);
}
