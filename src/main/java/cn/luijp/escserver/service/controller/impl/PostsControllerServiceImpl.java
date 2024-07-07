package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.Exception.PostNotFoundException;
import cn.luijp.escserver.mapper.PostCategoriesMapper;
import cn.luijp.escserver.mapper.PostTagsMapper;
import cn.luijp.escserver.model.dto.PostsListDto;
import cn.luijp.escserver.model.dto.PostsWithTC;
import cn.luijp.escserver.model.entity.*;
import cn.luijp.escserver.model.vo.PostCategoriesWithCategoriesVo;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import cn.luijp.escserver.service.controller.PostsControllerService;
import cn.luijp.escserver.service.db.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostsControllerServiceImpl implements PostsControllerService {

    private final IPostsService postsService;

    private final IPostTagsService postTagsService;

    private final IPostCategoriesService postCategoriesService;

    private final ITagsService tagsService;

    private final ICategoriesService categoriesService;

    private final PostTagsMapper postTagsMapper;

    private final PostCategoriesMapper postCategoriesMapper;

    @Autowired
    public PostsControllerServiceImpl(IPostsService postsService,
                                      IPostTagsService postTagsService,
                                      IPostCategoriesService postCategoriesService,
                                      ITagsService tagsService,
                                      ICategoriesService categoriesService,
                                      PostTagsMapper postTagsMapper,
                                      PostCategoriesMapper postCategoriesMapper) {
        this.postsService = postsService;
        this.postTagsService = postTagsService;
        this.postCategoriesService = postCategoriesService;
        this.tagsService = tagsService;
        this.categoriesService = categoriesService;
        this.postTagsMapper = postTagsMapper;
        this.postCategoriesMapper = postCategoriesMapper;
    }

    public Boolean updatePost(Posts posts) {
        return postsService.saveOrUpdate(posts);
    }

    public Boolean delPost(Long id) {
        return postsService.removeById(id);
    }

    public Posts getPost(Long id) {
        Posts post = postsService.getById(id);
        if (post == null) {
            throw new PostNotFoundException();
        }
        return post;
    }

    public PostsListDto getPostsList(Integer pageNum, Integer pageSize, Integer type) {

        IPage<Posts> postsPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Posts> postsQueryWrapper = new LambdaQueryWrapper<>();
        postsQueryWrapper.eq(Posts::getType, type).eq(Posts::getVisible, true);

        //获得文章列表
        IPage<Posts> postPage = postsService.page(postsPage, postsQueryWrapper);
        List<Posts> postsList = postPage.getRecords();

        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setSize(pageSize);
        postsListDto.setPage(pageNum);
        postsListDto.setTotal(postPage.getTotal());
        postsListDto.setPostsList(new ArrayList<>());

        //获得文章ID
        List<Long> postIdsList = postsList.stream().map(Posts::getId).toList();

        if (postIdsList.isEmpty()) {
            return postsListDto;
        }

        //获取文章TAG
        LambdaQueryWrapper<PostTags> postTagsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        postTagsLambdaQueryWrapper.in(PostTags::getPostId, postIdsList);
        List<PostTagsWithTagsVo> postTagsWithTagsList = postTagsMapper.getPostTagsWithTags(postTagsLambdaQueryWrapper);

        //获取文章分类
        LambdaQueryWrapper<PostCategories> postCategoriesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        postCategoriesLambdaQueryWrapper.in(PostCategories::getPostId, postIdsList);
        List<PostCategoriesWithCategoriesVo> postCategoriesWithCategoriesList = postCategoriesMapper.getPostCategoriesWithCategories(postCategoriesLambdaQueryWrapper);

        //将分类信息和TAGS插入文章数据中
        List<PostsWithTC> postsWithTCList = new ArrayList<>();
        postsList.forEach(item -> {
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
            pwt.setTags(postTagsWithTagsList);
            pwt.setCategories(postCategoriesWithCategoriesList);
            postsWithTCList.add(pwt);

        });

        postsListDto.setPostsList(postsWithTCList);

        return postsListDto;
    }

    public Boolean addTags(Long postId, List<Long> tagIds) {
        LambdaQueryWrapper<PostTags> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PostTags::getPostId, postId);
        postTagsService.remove(queryWrapper);

        List<PostTags> postTagsList = new ArrayList<>();
        List<Long> tagsList = tagsService.list().stream().map(Tags::getId).toList();
        tagIds.forEach(item -> {
            if (tagsList.contains(item)) {
                PostTags pt = new PostTags();
                pt.setPostId(postId);
                pt.setTagId(item);
                postTagsList.add(pt);
            }
        });
        return postTagsService.saveBatch(postTagsList);
    }

    public Boolean addCategories(Long postId, List<Long> categoryIds) {
        LambdaQueryWrapper<PostCategories> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PostCategories::getPostId, postId);
        postCategoriesService.remove(queryWrapper);

        List<PostCategories> postCategoriesList = new ArrayList<>();
        List<Long> categoriesList = categoriesService.list().stream().map(Categories::getId).toList();
        categoryIds.forEach(item -> {
            if (categoriesList.contains(item)) {
                PostCategories pc = new PostCategories();
                pc.setPostId(postId);
                pc.setCategoryId(item);
                postCategoriesList.add(pc);
            }
        });
        return postCategoriesService.saveBatch(postCategoriesList);
    }
}
