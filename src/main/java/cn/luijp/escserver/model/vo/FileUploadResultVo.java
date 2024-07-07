package cn.luijp.escserver.model.vo;

import lombok.Data;

@Data
public class FileUploadResultVo {
    private String fileName;
    private String uuid;
    private Boolean success;
}
