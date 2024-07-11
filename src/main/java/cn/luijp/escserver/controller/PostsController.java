package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.PostsListDto;
import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Posts;
import cn.luijp.escserver.service.controller.PostsControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsControllerService postsControllerService;

    @Autowired
    public PostsController(PostsControllerService postsControllerService) {
        this.postsControllerService = postsControllerService;
    }

    @GetMapping("/list/{pageNum}/{pageSize}/{type}")
    public ResponseDto<PostsListDto> list(@PathVariable int pageNum, @PathVariable int pageSize, @PathVariable String type) {
        return ResponseDto.successWithData(postsControllerService.getPostsList(pageNum, pageSize, Integer.parseInt(type)));
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
    public ResponseDto<Posts> get(@PathVariable Long id) {
        Posts post = postsControllerService.getPost(id);
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
