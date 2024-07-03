package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@TableName("login")
@Data
public class Login {

    @Setter(lombok.AccessLevel.NONE)
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String token;

    private LocalDateTime loginTime;

    private String ip;

    private String driver;

}
