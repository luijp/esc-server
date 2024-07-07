package cn.luijp.escserver.model.dto;

import cn.luijp.escserver.model.entity.Attach;
import lombok.Data;

import java.util.List;

@Data
public class AttachListDto {

    List<Attach> attachList;

    private Long total;

    private Integer page;

    private Integer size;
}
