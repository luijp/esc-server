package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("login_failed")
public class LoginFailed {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String ip;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
