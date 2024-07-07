package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("posts")
@Data
public class Posts {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private String summary;

    /**
     * 缩略图
     */
    private String cover;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

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
