package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("post_categories")
@Data
public class PostCategories {

    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    private Integer postId;

    private Integer categoryId;

}
