package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.Exception.PostNotFoundException;
import cn.luijp.escserver.cache.CacheManager;
import cn.luijp.escserver.cache.CategoriesCache;
import cn.luijp.escserver.cache.TagsCache;
import cn.luijp.escserver.model.dto.PostsListDto;
import cn.luijp.escserver.model.dto.PostsWithTC;
import cn.luijp.escserver.model.entity.*;
import cn.luijp.escserver.service.IPostCategoriesService;
import cn.luijp.escserver.service.IPostTagsService;
import cn.luijp.escserver.service.IPostsService;
import cn.luijp.escserver.service.PostsControllerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostsControllerServiceImpl implements PostsControllerService {

    private final IPostsService postsService;

    private final IPostTagsService postTagsService;

    private final IPostCategoriesService postCategoriesService;

    private final CacheManager cacheManager;

    @Autowired
    public PostsControllerServiceImpl(IPostsService postsService,
                                      IPostTagsService postTagsService,
                                      IPostCategoriesService postCategoriesService,
                                      CacheManager cacheManager) {
        this.postsService = postsService;
        this.postTagsService = postTagsService;
        this.postCategoriesService = postCategoriesService;
        this.cacheManager = cacheManager;
    }

    public Boolean updatePost(Posts posts) {
        return postsService.saveOrUpdate(posts);
    }

    public Boolean delPost(Integer id) {
        return postsService.removeById(id);
    }

    public Posts getPost(Integer id) {
        Posts post = postsService.getById(id);
        if (post == null) {
            throw new PostNotFoundException();
        }
        return post;
    }

    public PostsListDto getPostsList(Integer pageNum, Integer pageSize, Integer type) {

        cacheManager.init();
        IPage<Posts> postsPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Posts> postsQueryWrapper = new QueryWrapper<>();
        postsQueryWrapper.eq("type", type).eq("visible",true);

        //获得文章列表
        IPage<Posts> postPage = postsService.page(postsPage, postsQueryWrapper);
        List<Posts> postsList = postPage.getRecords();

        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setSize(pageSize);
        postsListDto.setPage(pageNum);
        postsListDto.setTotal(postPage.getTotal());
        postsListDto.setPostsList(new ArrayList<>());

        //获得文章ID
        List<Integer> postIdsList = postsList.stream().map(Posts::getId).toList();

        if(postIdsList.isEmpty()){
            return postsListDto;
        }
        //获取文章TAG
        QueryWrapper<PostTags> postTagsQueryWrapper = new QueryWrapper<>();
        postTagsQueryWrapper.in("post_id", postIdsList);
        List<PostTags> postTagsList = postTagsService.list(postTagsQueryWrapper);
        Map<Integer,List<Integer>> postTagsMap = new HashMap<>();
        postTagsList.forEach(item -> {
            if(postTagsMap.containsKey(item.getPostId()) && !postTagsMap.get(item.getPostId()).isEmpty()){
                postTagsMap.get(item.getPostId()).add(item.getTagId());
            }else{
                List<Integer> tagIds = new ArrayList<>();
                tagIds.add(item.getTagId());
                postTagsMap.put(item.getPostId(), tagIds);
            }

        });

        //获取文章分类
        QueryWrapper<PostCategories> postCategoriesQueryWrapper = new QueryWrapper<>();
        postCategoriesQueryWrapper.in("post_id", postIdsList);
        List<PostCategories> postCategoriesList = postCategoriesService.list(postCategoriesQueryWrapper);
        Map<Integer,List<Integer>> postCategoriesMap = new HashMap<>();
        postCategoriesList.forEach(item -> {
            if(postCategoriesMap.containsKey(item.getPostId()) && !postCategoriesMap.get(item.getPostId()).isEmpty()){
                postCategoriesMap.get(item.getPostId()).add(item.getCategoryId());
            }else{
                List<Integer> categoryId = new ArrayList<>();
                categoryId.add(item.getCategoryId());
                postCategoriesMap.put(item.getPostId(), categoryId);
            }
        });

        //将分类信息和TAGS插入文章数据中
        List<PostsWithTC> postsWithTCList = new ArrayList<>();
        postsList.forEach(item->{
            PostsWithTC pwt = new PostsWithTC();
            pwt.setId(item.getId());
            pwt.setTitle(item.getTitle());
            pwt.setContent("");
            pwt.setSummary(item.getSummary());
            pwt.setCreateTime(item.getCreateTime());
            pwt.setUpdateTime(item.getUpdateTime());
            pwt.setVisible(item.getVisible());
            pwt.setEncrypt(item.getEncrypt());
            pwt.setType(item.getType());


            List<Tags> tagsList = new ArrayList<>();
            if(postTagsMap.containsKey(item.getId())){
                postTagsMap.get(item.getId()).forEach(tagsItem ->{
                    tagsList.add(TagsCache.tagsMap.get(tagsItem));
                });
            }
            pwt.setTags(tagsList);

            List<Categories> categoriesList = new ArrayList<>();
            if(postCategoriesMap.containsKey(item.getId())) {
                postCategoriesMap.get(item.getId()).forEach(categoriesItem -> {
                    categoriesList.add(CategoriesCache.CategoriesMap.get(categoriesItem));
                });
            }
            pwt.setCategories(categoriesList);
            postsWithTCList.add(pwt);

        });

        postsListDto.setPostsList(postsWithTCList);

        return postsListDto;
    }

    public Boolean addTags(Integer postId, List<Integer> tagIds) {
        QueryWrapper<PostTags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        postTagsService.remove(queryWrapper);

        List<PostTags> postTagsList = new ArrayList<>();
        tagIds.forEach(item->{
            PostTags pt = new PostTags();
            pt.setPostId(postId);
            pt.setTagId(item);
            postTagsList.add(pt);
        });
        return postTagsService.saveBatch(postTagsList);
    }

    public Boolean addCategories(Integer postId, List<Integer> categoryIds) {
        QueryWrapper<PostCategories> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        postCategoriesService.remove(queryWrapper);

        List<PostCategories> postCategories = new ArrayList<>();
        categoryIds.forEach(item->{
            PostCategories pc = new PostCategories();
            pc.setPostId(postId);
            pc.setCategoryId(item);
            postCategories.add(pc);
        });
        return postCategoriesService.saveBatch(postCategories);
    }
}
