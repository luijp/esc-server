package cn.luijp.escserver.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoriesAllDto {

    List<CategoriesAllDto> children;
    private Long id;
    private String name;
    private String alias;
    private Long parentId;

}
