package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private Long id;

    private Long postId;

    private String username;

    private String email;

    private String content;

    private Boolean visible;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
