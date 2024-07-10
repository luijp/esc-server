package cn.luijp.escserver.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Attach {

    @TableId
    private String uuid;

    private String name;

    private String path;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
