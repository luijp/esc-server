package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Setter;
import java.time.LocalDateTime;

@TableName("login")
@Data
public class Login {

    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    private String token;

    private LocalDateTime loginTime;

    private String ip;

    private String driver;

}
