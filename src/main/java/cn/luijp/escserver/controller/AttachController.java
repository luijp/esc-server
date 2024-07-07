package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.service.controller.AttachControllerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attach")
public class AttachController {

    private final AttachControllerService attachControllerService;

    @Autowired
    public AttachController(AttachControllerService attachControllerService) {
        this.attachControllerService = attachControllerService;
    }

    @PostMapping("/upload")
    public ResponseDto<Object> upload(@RequestParam("file") MultipartFile file) {
        String upload = attachControllerService.upload(file);
        if (upload == null) {
            return ResponseDto.error(-1,"Upload failed");
        }else{
            return ResponseDto.success();
        }
    }

    @GetMapping("/image/{uuid}")
    public byte[] image(HttpServletResponse response, @PathVariable String uuid) {
        response.reset();
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + attachControllerService.getOriginName(uuid) + "\"");
        return attachControllerService.get(uuid);
    }

    @GetMapping("/download/{uuid}")
    public byte[] download(HttpServletResponse response, @PathVariable String uuid) {
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + attachControllerService.getOriginName(uuid) + "\"");
        return attachControllerService.get(uuid);
    }

    @PostMapping("/del/{uuid}")
    public ResponseDto<Object> del(@PathVariable String uuid) {
        Boolean status = attachControllerService.del(uuid);
        if(status){
            return ResponseDto.success();
        }else{
            return ResponseDto.error(-1,"Delete failed");
        }
    }

}
