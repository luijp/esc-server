package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("custom_settings")
@Data
public class CustomSettings {

    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    private String k;

    private String v;

}
