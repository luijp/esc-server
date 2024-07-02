package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("post_tags")
@Data
public class PostTags {

    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    private Integer tagId;

    private Integer postId;

}
