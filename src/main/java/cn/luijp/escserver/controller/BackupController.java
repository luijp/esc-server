package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.BackupFileNameDto;
import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.model.entity.Login;
import cn.luijp.escserver.service.controller.AuthControllerService;
import cn.luijp.escserver.service.controller.BackupControllerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backup")
public class BackupController {

    @Autowired
    private final BackupControllerService backupControllerService;

    @Autowired
    private final AuthControllerService authControllerService;

    public BackupController(BackupControllerService backupControllerService, AuthControllerService authControllerService) {
        this.backupControllerService = backupControllerService;
        this.authControllerService = authControllerService;
    }


    @PostMapping("/backup-database")
    public ResponseDto<Boolean> backupDatabase() {
        Boolean result = backupControllerService.backupDatabase();
        if(result){
            return ResponseDto.success();
        }else{
            return ResponseDto.error(-1,"backup database failed");
        }
    }

    @PostMapping("/backup-upload")
    public ResponseDto<Boolean> backupUpload() {
        Boolean result = backupControllerService.backupUpload();
        if(result){
            return ResponseDto.success();
        }else{
            return ResponseDto.error(-1,"backup upload failed");
        }
    }

    @PostMapping("/delete-backup")
    public ResponseDto<Boolean> deleteBackup(@RequestBody BackupFileNameDto fileName) {
        Boolean result = backupControllerService.deleteBackup(fileName);
        if(result){
            return ResponseDto.success();
        }else{
            return ResponseDto.error(-1,"delete backup failed");
        }
    }

    @PostMapping("/list-backup")
    public ResponseDto<List<String>> listBackup() {
        List<String> result = backupControllerService.listBackup();
        if(result != null){
            return ResponseDto.successWithData(result);
        }else{
            return ResponseDto.error(-1,"backup list failed");
        }
    }

    @GetMapping("/get/{fileName}")
    public ResponseEntity<FileSystemResource> getBackup(@PathVariable String fileName, HttpServletRequest request) {
        Login auth = authControllerService.auth(request);
        if(auth == null){
            return null;
        }
        return backupControllerService.getBackup(fileName);
    }
}
