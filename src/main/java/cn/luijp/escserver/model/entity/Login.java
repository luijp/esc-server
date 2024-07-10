package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("login")
@Data
public class Login {

    private String uuid;

    private String token;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String ip;

    private String driver;

}
