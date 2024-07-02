package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("categories")
@Data
public class Categories {

    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    private String name;

    private String alias;

    private Integer parentId;

}
