package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long postId;

    private String username;

    private String email;

    private String content;

    private Boolean visible;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
