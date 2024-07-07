package cn.luijp.escserver.model.dto;

import cn.luijp.escserver.model.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class CommentListDto {

    private List<Comment> commentList;

    private Long total;

    private Integer pageNum;

    private Integer pageSize;
}
