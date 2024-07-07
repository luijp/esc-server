package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.dto.AttachListDto;
import org.springframework.web.multipart.MultipartFile;

public interface AttachControllerService {

    byte[] get(String uuid);

    String upload(MultipartFile file);

    Boolean del(String uuid);

    String getOriginName(String uuid);

    AttachListDto list(Integer pageNum, Integer pageSize);
}
