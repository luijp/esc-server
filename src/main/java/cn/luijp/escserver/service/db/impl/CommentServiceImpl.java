package cn.luijp.escserver.service.db.impl;

import cn.luijp.escserver.mapper.CommentMapper;
import cn.luijp.escserver.model.entity.Comment;
import cn.luijp.escserver.service.db.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
}
