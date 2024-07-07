package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.CommentListDto;
import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Comment;
import cn.luijp.escserver.service.controller.CommentControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentControllerService commentControllerService;

    @Autowired
    public CommentController(CommentControllerService commentControllerService) {
        this.commentControllerService = commentControllerService;
    }

    @GetMapping("/list/{postId}/{pageNum}/{pageSize}")
    public ResponseDto<CommentListDto> list(@PathVariable Long postId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        CommentListDto comment = commentControllerService.getComment(postId, pageNum, pageSize);
        return ResponseDto.successWithData(comment);
    }

    @PostMapping("/add")
    public ResponseDto<Object> add(@RequestBody Comment comment) {
        Boolean status = commentControllerService.addComment(comment);
        if(status){
            return ResponseDto.success();
        }else{
            return ResponseDto.error(-1,"Comment add failed");
        }
    }

    @PostMapping("/del/{id}")
    public ResponseDto<Object> del(@PathVariable Long id) {
        Boolean status = commentControllerService.delComment(id);
        if(status){
            return ResponseDto.success();
        }else {
            return ResponseDto.error(-1,"Comment delete failed");
        }
    }

    @PostMapping("/pass/{id}")
    public ResponseDto<Object> pass(@PathVariable Long id) {
        Boolean status = commentControllerService.passComment(id);
        if(status){
            return ResponseDto.success();
        }else {
            return ResponseDto.error(-1,"Comment pass failed");
        }
    }
}
