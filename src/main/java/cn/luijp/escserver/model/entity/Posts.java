package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("posts")
@Data
public class Posts {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String summary;

    /**
     * 缩略图
     */
    private String cover;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否隐藏，-1:全部，0:隐藏，1:显示，2:登录可见
     */
    private Integer visible;

    /**
     * 是否加密
     */
    private String encrypt;

    /**
     * 1:文章，2:页面，3:碎语, 4:相册
     */
    private Integer type;

}
