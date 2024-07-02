package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("auth")
public class Auth {

    private String username;

    private String password;
}
