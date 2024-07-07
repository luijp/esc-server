package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("categories")
@Data
public class Categories {

    @Setter(lombok.AccessLevel.NONE)
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String alias;

    private Long parentId;

}
