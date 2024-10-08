package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.Exception.AttachFileNotFoundException;
import cn.luijp.escserver.model.dto.AttachListDto;
import cn.luijp.escserver.model.entity.Attach;
import cn.luijp.escserver.service.controller.AttachControllerService;
import cn.luijp.escserver.service.db.IAttachService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AttachControllerServiceImpl implements AttachControllerService {

    private final IAttachService attachService;
    @Value("${esc.upload-path}")
    private String filePath;

    @Autowired
    public AttachControllerServiceImpl(IAttachService attachService) {
        this.attachService = attachService;
    }

    public void get(String uuid, HttpServletResponse response) {
        if (uuid == null || uuid.isEmpty()) {
            throw new AttachFileNotFoundException();
        }
        Attach attach = attachService.getById(uuid);
        if (attach == null) {
            throw new AttachFileNotFoundException();
        }

        try {

            FileInputStream file = new FileInputStream(filePath + "/" + attach.getPath() + "/" + attach.getUuid());
            BufferedInputStream bis = new BufferedInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        } catch (Exception ex) {
            throw new AttachFileNotFoundException();
        }

    }

    public String getOriginName(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            throw new AttachFileNotFoundException();
        }
        Attach attach = attachService.getById(uuid);
        if (attach == null) {
            throw new AttachFileNotFoundException();
        }
        return attach.getName();
    }

    @Override
    public AttachListDto list(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Attach> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Attach::getCreateTime);
        IPage<Attach> attachPage = attachService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Attach> attachList = attachPage.getRecords();
        AttachListDto attachListDto = new AttachListDto();
        attachListDto.setTotal(attachPage.getTotal());
        attachListDto.setSize(pageSize);
        attachListDto.setPage(pageNum);
        attachListDto.setAttachList(attachList);
        return attachListDto;
    }

    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            String uuid = UUID.randomUUID().toString();
            Attach attach = new Attach();
            attach.setUuid(uuid);
            attach.setName(file.getOriginalFilename());
            attach.setCreateTime(LocalDateTime.now());
            attach.setPath(Integer.toString(LocalDateTime.now().getYear()));

            File dest = new File(filePath + "/" + attach.getPath() + "/" + uuid);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);

            attachService.save(attach);
            return uuid;

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    public Boolean del(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            return false;
        }
        try {
            Attach attach = attachService.getById(uuid);
            File file = new File(filePath + "/" + attach.getPath() + "/" + uuid);
            file.delete();
            return attachService.removeById(uuid);
        } catch (Exception ex) {
            return false;
        }

    }
}
