package cn.luijp.escserver.service.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AttachControllerService {

    public byte[] get(String uuid);

    public String upload(MultipartFile file);

    public Boolean del(String uuid);

    public String getOriginName(String uuid);
}
