package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.dto.CommentListDto;
import cn.luijp.escserver.model.entity.Comment;

import java.util.List;

public interface CommentControllerService {

    public Boolean addComment(Comment comment);

    public Boolean delComment(Long id);

    public CommentListDto getComment(Long postId, Integer pageNum, Integer pageSize);

    public Boolean passComment(Long id);
}
