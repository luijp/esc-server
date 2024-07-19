package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.model.dto.CommentListDto;
import cn.luijp.escserver.model.entity.Comment;
import cn.luijp.escserver.service.controller.CommentControllerService;
import cn.luijp.escserver.service.db.ICommentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentControllerServiceImpl implements CommentControllerService {

    private final ICommentService commentService;

    @Autowired
    public CommentControllerServiceImpl(ICommentService commentService) {
        this.commentService = commentService;
    }

    public Long addComment(Comment comment) {
        comment.setVisible(false);
        boolean status = commentService.save(comment);
        if(status){
            return comment.getId();
        }else{
            return null;
        }
    }

    public Boolean delComment(Long id) {
        return commentService.removeById(id);
    }

    public CommentListDto getComment(Long postId, Integer pageNum, Integer pageSize, Boolean visible) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        if(postId != null){
            queryWrapper.eq(Comment::getPostId, postId);
        }
        if (visible != null){
            queryWrapper.eq(Comment::getVisible,visible);
        }
        Page<Comment> commentPage = commentService.page(new Page<>(pageNum, pageSize), queryWrapper);
        CommentListDto commentListDto = new CommentListDto();
        commentListDto.setCommentList(commentPage.getRecords());
        commentListDto.setTotal(commentPage.getTotal());
        commentListDto.setPageNum(pageNum);
        commentListDto.setPageSize(pageSize);
        return commentListDto;
    }

    public Boolean passComment(Long id) {
        LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Comment::getId, id).set(Comment::getVisible, true);
        return commentService.update(updateWrapper);
    }

    public Boolean denyComment(Long id) {
        LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Comment::getId, id).set(Comment::getVisible, false);
        return commentService.update(updateWrapper);
    }
}
