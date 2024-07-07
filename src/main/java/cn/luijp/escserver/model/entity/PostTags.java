package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("post_tags")
@Data
public class PostTags {

    @Setter(lombok.AccessLevel.NONE)
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tagId;

    private Long postId;

}
