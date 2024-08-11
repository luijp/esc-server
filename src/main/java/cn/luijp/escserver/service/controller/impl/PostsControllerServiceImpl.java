package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.Exception.PostNotFoundException;
import cn.luijp.escserver.mapper.PostCategoriesMapper;
import cn.luijp.escserver.mapper.PostTagsMapper;
import cn.luijp.escserver.model.dto.PostsListDto;
import cn.luijp.escserver.model.dto.PostsWithTC;
import cn.luijp.escserver.model.entity.*;
import cn.luijp.escserver.service.controller.PostsControllerService;
import cn.luijp.escserver.service.db.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostsControllerServiceImpl implements PostsControllerService {

    private final IPostsService postsService;

    private final IPostTagsService postTagsService;

    private final IPostCategoriesService postCategoriesService;

    private final ITagsService tagsService;

    private final ICategoriesService categoriesService;

    private final PostTagsMapper postTagsMapper;

    private final PostCategoriesMapper postCategoriesMapper;

    private final CategoriesControllerServiceImpl categoriesControllerService;

    private final TagsControllerServiceImpl tagsControllerService;
    private final TagsControllerServiceImpl tagsControllerServiceImpl;

    @Autowired
    public PostsControllerServiceImpl(IPostsService postsService,
                                      IPostTagsService postTagsService,
                                      IPostCategoriesService postCategoriesService,
                                      ITagsService tagsService,
                                      ICategoriesService categoriesService,
                                      PostTagsMapper postTagsMapper,
                                      PostCategoriesMapper postCategoriesMapper,
                                      CategoriesControllerServiceImpl categoriesControllerService,
                                      TagsControllerServiceImpl tagsControllerService, TagsControllerServiceImpl tagsControllerServiceImpl) {
        this.postsService = postsService;
        this.postTagsService = postTagsService;
        this.postCategoriesService = postCategoriesService;
        this.tagsService = tagsService;
        this.categoriesService = categoriesService;
        this.postTagsMapper = postTagsMapper;
        this.postCategoriesMapper = postCategoriesMapper;
        this.categoriesControllerService = categoriesControllerService;
        this.tagsControllerService = tagsControllerService;
        this.tagsControllerServiceImpl = tagsControllerServiceImpl;
    }

    public Long updatePost(Posts posts) {
        boolean status = postsService.saveOrUpdate(posts);
        if (status) {
            return posts.getId();
        }
        return null;
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

    public PostsListDto getPostsList(Integer pageNum, Integer pageSize, Integer type, Integer visible) {
        return packagePost(pageNum, pageSize, type, 1, null, visible);
    }

    public PostsListDto getPostsListByTag(Integer pageNum, Integer pageSize, Integer type, Long tagId, Integer visible) {
        return packagePost(pageNum, pageSize, type, 2, tagId, visible);
    }

    public PostsListDto getPostsListByCategory(Integer pageNum, Integer pageSize, Integer type, Long categoryId, Integer visible) {
        return packagePost(pageNum, pageSize, type, 3, categoryId, visible);
    }

    private PostsListDto packagePost(Integer pageNum, Integer pageSize, Integer type, Integer filter, Long id, Integer visible) {

        IPage<Posts> postsPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Posts> postsQueryWrapper = new LambdaQueryWrapper<>();
        if (visible == 2) {
            postsQueryWrapper.in(Posts::getVisible, Arrays.asList(1, 2));
        } else if (visible == -1) {
        } else {
            postsQueryWrapper.eq(Posts::getVisible, visible);
        }
        if (filter == 1) {
            //全部
            postsQueryWrapper.eq(Posts::getType, type);
        } else if (filter == 2) {
            //TAG
            LambdaQueryWrapper<PostTags> postTagsLambdaQueryWrapper = new LambdaQueryWrapper<>();
            postTagsLambdaQueryWrapper.eq(PostTags::getTagId, id);
            List<Long> postTagsList = postTagsService.list(postTagsLambdaQueryWrapper).stream().map(PostTags::getPostId).toList();
            if (postTagsList.isEmpty()) {
                return null;
            }
            postsQueryWrapper.eq(Posts::getType, 1).in(Posts::getId, postTagsList);
        } else if (filter == 3) {
            LambdaQueryWrapper<PostCategories> postCategoriesLambdaQueryWrapper = new LambdaQueryWrapper<>();
            postCategoriesLambdaQueryWrapper.eq(PostCategories::getCategoryId, id);
            List<Long> postCategoriesList = postCategoriesService.list(postCategoriesLambdaQueryWrapper).stream().map(PostCategories::getPostId).collect(Collectors.toList());
            if (postCategoriesList.isEmpty()) {
                return null;
            }
            postsQueryWrapper.eq(Posts::getType, 1).in(Posts::getId, postCategoriesList);
        }
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
            pwt.setTags(tagsControllerServiceImpl.getTagsByPostId(item.getId()));
            pwt.setCategories(categoriesControllerService.getCategoriesByPostId(item.getId()));
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
