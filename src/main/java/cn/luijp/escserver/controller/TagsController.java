package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.model.vo.PostTagsWithTagsVo;
import cn.luijp.escserver.service.controller.TagsControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    private final TagsControllerService tagsControllerService;

    @Autowired
    public TagsController(TagsControllerService tagsControllerService) {
        this.tagsControllerService = tagsControllerService;
    }

    @GetMapping("/")
    public ResponseDto<List<Tags>> getTags() {
        return ResponseDto.successWithData(tagsControllerService.getAllTags());
    }

    @GetMapping("/get/{post_id}")
    public ResponseDto<List<PostTagsWithTagsVo>> getTagsByPostId(@PathVariable Long post_id) {
        return ResponseDto.successWithData(tagsControllerService.getTagsByPostId(post_id));
    }

    @PostMapping("/update")
    public ResponseDto<Long> updateTag(@RequestBody Tags tag) {
        Long id = tagsControllerService.updateTag(tag);
        if (id != null) {
            return ResponseDto.successWithData(id);
        } else {
            return ResponseDto.error(-1, "Tag update failed");
        }
    }

    @PostMapping("/del")
    public ResponseDto<Boolean> delTag(@RequestBody Tags tag) {
        Boolean status = tagsControllerService.delTag(tag);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Tag delete failed");
        }
    }

    @PostMapping("/del/{id}")
    public ResponseDto<Boolean> delTagById(@PathVariable Long id) {
        Boolean status = tagsControllerService.delTag(id);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Tag delete failed");
        }
    }
}
