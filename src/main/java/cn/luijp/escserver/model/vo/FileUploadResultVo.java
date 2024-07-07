package cn.luijp.escserver.model.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FileUploadResultVo {
    private String fileName;
    private String uuid;
    private Boolean success;
}
