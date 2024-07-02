package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

@TableName("global_settings")
@Data
public class GlobalSettings {

    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    private String k;

    private String v;

}
