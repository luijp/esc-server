package cn.luijp.escserver.service.controller;

import cn.luijp.escserver.model.dto.BackupFileNameDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BackupControllerService {

    Boolean backupDatabase();

    Boolean backupUpload();

    ResponseEntity<FileSystemResource> getBackup(String fileName);

    List<String> listBackup();

    Boolean deleteBackup(BackupFileNameDto fileName);
}
