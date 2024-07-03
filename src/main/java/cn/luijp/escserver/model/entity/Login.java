package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("login")
@Data
public class Login {

    private String uuid;

    private String token;

    private LocalDateTime loginTime;

    private String ip;

    private String driver;

}
