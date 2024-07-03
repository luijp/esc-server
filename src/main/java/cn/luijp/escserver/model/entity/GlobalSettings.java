package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("global_settings")
@Data
public class GlobalSettings {

    @TableId(type = IdType.INPUT)
    private String k;

    private String v;

}
