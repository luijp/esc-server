package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.dto.AttachListDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AttachControllerService {

    void get(String uuid, HttpServletResponse response);

    String upload(MultipartFile file);

    Boolean del(String uuid);

    String getOriginName(String uuid);

    AttachListDto list(Integer pageNum, Integer pageSize);
}
