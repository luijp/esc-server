package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.AttachListDto;
import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.vo.FileUploadResultVo;
import cn.luijp.escserver.service.controller.AttachControllerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/attach")
public class AttachController {

    private final AttachControllerService attachControllerService;

    @Autowired
    public AttachController(AttachControllerService attachControllerService) {
        this.attachControllerService = attachControllerService;
    }

    @PostMapping("/upload")
    public ResponseDto<Object> upload(@RequestParam("files") MultipartFile[] files) {
        List<FileUploadResultVo> fileUploadResultList = new ArrayList<>();
        for (MultipartFile file : files) {
            FileUploadResultVo fileUploadResultVo = new FileUploadResultVo();
            fileUploadResultVo.setFileName(file.getOriginalFilename());
            fileUploadResultVo.setSuccess(true);
            String uuid = attachControllerService.upload(file);
            if (uuid == null) {
                fileUploadResultVo.setSuccess(false);
            } else {
                fileUploadResultVo.setUuid(uuid);
            }
            fileUploadResultList.add(fileUploadResultVo);
        }
        return ResponseDto.successWithData(fileUploadResultList);
    }

    @PostMapping("/list/{pageNum}/{pageSize}")
    public ResponseDto<AttachListDto> list(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        AttachListDto attachListDto = attachControllerService.list(pageNum, pageSize);
        return ResponseDto.successWithData(attachListDto);
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
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Delete failed");
        }
    }

}
