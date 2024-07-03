package cn.luijp.escserver.service;

import cn.luijp.escserver.model.dto.PostsListDto;
import cn.luijp.escserver.model.entity.Posts;

import java.util.List;

public interface PostsControllerService {

    public Boolean updatePost(Posts posts);

    public Boolean delPost(Integer id);

    public Posts getPost(Integer id);

    public PostsListDto getPostsList(Integer pageNum, Integer pageSize,Integer type);

    public Boolean addTags(Integer postId, List<Integer> tagIds);

    public Boolean addCategories(Integer postId, List<Integer> categoryIds);
}
