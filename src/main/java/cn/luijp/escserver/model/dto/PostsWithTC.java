package cn.luijp.escserver.model.dto;

import cn.luijp.escserver.model.entity.Categories;
import cn.luijp.escserver.model.entity.Posts;
import cn.luijp.escserver.model.entity.Tags;
import lombok.Data;

import java.util.List;

@Data
public class PostsWithTC extends Posts {
    List<Tags> tags;
    List<Categories> categories;
    private Integer id;
}
