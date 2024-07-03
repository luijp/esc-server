package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Tags;
import cn.luijp.escserver.service.TagsControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    private final TagsControllerService tagsControllerService;

    @Autowired
    public TagsController(TagsControllerService tagsControllerService){
        this.tagsControllerService = tagsControllerService;
    }

    @GetMapping("/")
    public ResponseDto<List<Tags>> getTags(){
        return ResponseDto.successWithData(tagsControllerService.getAllTags());
    }

    @PostMapping("/update")
    public ResponseDto<Boolean> updateTags(@RequestBody Tags tag){
        Boolean status = tagsControllerService.updateTag(tag);
        if(status){
            return ResponseDto.success();
        }else{
            return ResponseDto.error(-1, "Update failed");
        }
    }

    @PostMapping("/del")
    public ResponseDto<Boolean> delTags(@RequestBody Tags tag){
        Boolean status = tagsControllerService.delTags(tag);
        if(status){
            return ResponseDto.success();
        }else{
            return ResponseDto.error(-1, "Delete failed");
        }
    }

    @PostMapping("/del/{id}")
    public ResponseDto<Boolean> delTagById(@PathVariable Integer id){
        Boolean status = tagsControllerService.delTags(id);
        if(status){
            return ResponseDto.success();
        }else{
            return ResponseDto.error(-1, "Delete failed");
        }
    }
}
