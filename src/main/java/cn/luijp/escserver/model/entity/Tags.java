package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("tags")
@Data
public class Tags {

    @Setter(lombok.AccessLevel.NONE)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String alias;
}
