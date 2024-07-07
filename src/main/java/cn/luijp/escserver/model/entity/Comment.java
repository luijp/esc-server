package cn.luijp.escserver.model.entity;

import lombok.Data;

@Data
public class Comment {

    private Long id;

    private Long postId;

    private String username;

    private String email;

    private String content;

    private Boolean visible;
}
