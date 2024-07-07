package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("post_categories")
@Data
public class PostCategories {

    @Setter(lombok.AccessLevel.NONE)
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long postId;

    private Long categoryId;

}
