package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@TableName("posts")
@Data
public class Posts {

    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    private String title;

    private String content;

    /**
     * 缩略图
     */
    private String cover;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 文章连接
     */
    private String slug;

    /**
     * 是否隐藏
     */
    private Boolean visible;

    /**
     * 是否加密
     */
    private String encrypt;

    /**
     * 1:文章，2:页面
     */
    private Integer type;

}
