package cn.luijp.escserver.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoriesAllDto {

    private Long id;

    private String name;

    private String alias;

    private Long parentId;

    List<CategoriesAllDto> children;

}
