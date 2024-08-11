package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.PostsListDto;
import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Login;
import cn.luijp.escserver.model.entity.Posts;
import cn.luijp.escserver.service.controller.AuthControllerService;
import cn.luijp.escserver.service.controller.PostsControllerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsControllerService postsControllerService;
    private final AuthControllerService authControllerService;

    @Autowired
    public PostsController(PostsControllerService postsControllerService, AuthControllerService authControllerService) {
        this.postsControllerService = postsControllerService;
        this.authControllerService = authControllerService;
    }

    @GetMapping("/list/{pageNum}/{pageSize}/{type}")
    public ResponseDto<PostsListDto> list(@PathVariable int pageNum,
                                          @PathVariable int pageSize,
                                          @PathVariable Integer type,
                                          HttpServletRequest request
    ) {
        Login auth = authControllerService.auth(request);
        Integer visible = 1;
        if (auth != null) {
            visible = 2;
        }
        return ResponseDto.successWithData(postsControllerService.getPostsList(pageNum, pageSize, type, visible));
    }

    @GetMapping("/list_full/{pageNum}/{pageSize}/{type}")
    public ResponseDto<PostsListDto> listFull(@PathVariable int pageNum,
                                              @PathVariable int pageSize,
                                              @PathVariable Integer type,
                                              HttpServletRequest request
    ) {
        Login auth = authControllerService.auth(request);
        Integer visible = 1;
        if (auth != null) {
            visible = 2;
        }
        return ResponseDto.successWithData(postsControllerService.getPostsList(pageNum, pageSize, type, visible));
    }

    @GetMapping("/list/tags/{pageNum}/{pageSize}/{type}/{id}")
    public ResponseDto<PostsListDto> listByTagId(@PathVariable int pageNum,
                                                 @PathVariable int pageSize,
                                                 @PathVariable Integer type,
                                                 @PathVariable Long id,
                                                 HttpServletRequest request
    ) {
        Login auth = authControllerService.auth(request);
        Integer visible = 1;
        if (auth != null) {
            visible = 2;
        }
        return ResponseDto.successWithData(postsControllerService.getPostsListByTag(pageNum, pageSize, type, id, visible));
    }

    @GetMapping("/list/categories/{pageNum}/{pageSize}/{type}/{id}")
    public ResponseDto<PostsListDto> listByCategoryId(@PathVariable int pageNum,
                                                      @PathVariable int pageSize,
                                                      @PathVariable Integer type,
                                                      @PathVariable Long id,
                                                      HttpServletRequest request
    ) {
        Login auth = authControllerService.auth(request);
        Integer visible = 1;
        if (auth != null) {
            visible = 2;
        }
        return ResponseDto.successWithData(postsControllerService.getPostsListByCategory(pageNum, pageSize, type, id, visible));
    }

    @PostMapping("/update")
    public ResponseDto<Long> update(@RequestBody Posts posts) {
        Long id = postsControllerService.updatePost(posts);
        if (id != null) {
            return ResponseDto.successWithData(id);
        } else {
            return ResponseDto.error(-1, "Post update failed");
        }
    }

    @PostMapping("/del/{id}")
    public ResponseDto<Posts> del(@PathVariable Long id) {
        Boolean status = postsControllerService.delPost(id);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Post del failed");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseDto<Posts> get(@PathVariable Long id,HttpServletRequest request) {
        Posts post = postsControllerService.getPost(id);
        if(post == null){
            return ResponseDto.error(-404, "Post not found");
        }
        Login auth = authControllerService.auth(request);
        if (auth == null) {
            if(post.getVisible() != 1){
                return ResponseDto.error(-403, "Auth required");
            }
        }
        return ResponseDto.successWithData(post);
    }

    @PostMapping("/add/tags/{post_id}")
    public ResponseDto<Posts> addTags(@RequestBody List<Long> tags, @PathVariable Long post_id) {
        Boolean status = postsControllerService.addTags(post_id, tags);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Post add tags failed");
        }
    }

    @PostMapping("/add/categories/{post_id}")
    public ResponseDto<Posts> addCategories(@RequestBody List<Long> categories, @PathVariable Long post_id) {
        Boolean status = postsControllerService.addCategories(post_id, categories);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Post add categories failed");
        }
    }
}
