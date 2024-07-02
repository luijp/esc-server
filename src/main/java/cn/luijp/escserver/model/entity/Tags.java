package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("tags")
@Data
public class Tags {

    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    @Setter
    private String name;

    @Setter
    private String alias;
}
