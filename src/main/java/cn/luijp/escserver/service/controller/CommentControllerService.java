package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.dto.CommentListDto;
import cn.luijp.escserver.model.entity.Comment;

public interface CommentControllerService {

    Long addComment(Comment comment);

    Boolean delComment(Long id);

    CommentListDto getComment(Long postId, Integer pageNum, Integer pageSize, Boolean visible);

    Boolean passComment(Long id);

    Boolean denyComment(Long id);
}
