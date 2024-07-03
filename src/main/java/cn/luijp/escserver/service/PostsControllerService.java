package cn.luijp.escserver.service;

import cn.luijp.escserver.model.dto.PostsListDto;
import cn.luijp.escserver.model.entity.Posts;

import java.util.List;

public interface PostsControllerService {

    Boolean updatePost(Posts posts);

    Boolean delPost(Integer id);

    Posts getPost(Integer id);

    PostsListDto getPostsList(Integer pageNum, Integer pageSize, Integer type);

    Boolean addTags(Integer postId, List<Integer> tagIds);

    Boolean addCategories(Integer postId, List<Integer> categoryIds);
}
