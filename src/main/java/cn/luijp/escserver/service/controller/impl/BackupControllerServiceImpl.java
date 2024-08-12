package cn.luijp.escserver.service.controller.impl;

import cn.luijp.escserver.model.dto.BackupFileNameDto;
import cn.luijp.escserver.service.controller.BackupControllerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class BackupControllerServiceImpl implements BackupControllerService {
    @Value("${spring.datasource.username}")
    private String DBUser;

    @Value("${spring.datasource.password}")
    private String DBPwd;

    @Value("${esc.backup-db}")
    private String DBName;

    @Value("${esc.backup-path}")
    private String BackupFolder;

    @Value("${esc.upload-path}")
    private String UploadFolder;

    @Value("${esc.backup-dbhost}")
    private String DBHost;

    private static void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                zipDirectory(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(parentFolder + "/" + file.getName());
            zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes);
            }
            zos.closeEntry();
            fis.close();
        }
    }

    public Boolean backupDatabase() {
        try {
            File dest = new File(BackupFolder + "/db");
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            String command = String.format(
                    "\"C:\\Program Files\\MariaDB 11.5\\bin\\mysqldump.exe\" -h%s -u%s -p%s %s > %s",
                    DBHost, DBUser, DBPwd, DBName, BackupFolder + "/db" + Instant.now().getEpochSecond() + ".sql"
            );
            Process process = Runtime.getRuntime().exec(new String[]{"/usr/bin/bash", "/c", command});
            process.waitFor(); // 等待命令执行完成
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean backupUpload() {
        try {
            String sourceDirPath = UploadFolder; // 要打包的目录路径
            String zipFilePath = BackupFolder + "/upload" + Instant.now().getEpochSecond() + ".zip"; // 输出的ZIP文件路径

            try {
                FileOutputStream fos = new FileOutputStream(zipFilePath);
                ZipOutputStream zos = new ZipOutputStream(fos);
                File sourceDir = new File(sourceDirPath);
                zipDirectory(sourceDir, sourceDir.getName(), zos);
                zos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ResponseEntity<FileSystemResource> getBackup(String fileName) {
        File file = new File(BackupFolder + "/" + fileName);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        FileSystemResource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    public List<String> listBackup() {
        List<String> fileList = new ArrayList<>();
        File directory = new File(BackupFolder);

        // 确保目录存在
        if (!directory.exists() || !directory.isDirectory()) {
            return null;
        }

        // 获取目录下的所有文件和文件夹
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                fileList.add(file.getName());
            }
        }
        return fileList;
    }

    public Boolean deleteBackup(BackupFileNameDto fileName) {
        File file = new File(BackupFolder + "/" + fileName.getFileName());

        // 检查文件是否存在
        if (file.exists()) {
            // 尝试删除文件
            boolean isDeleted = file.delete();

            if (isDeleted) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
