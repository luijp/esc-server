package cn.luijp.escserver.controller;

import cn.luijp.escserver.model.dto.ResponseDto;
import cn.luijp.escserver.service.controller.SettingsControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsControllerService settingsControllerService;

    @Autowired
    public SettingsController(SettingsControllerService settingsControllerService) {
        this.settingsControllerService = settingsControllerService;
    }

    @GetMapping("/")
    public ResponseDto<Map<String, Map<String, String>>> getSettings() {
        Map<String, Map<String, String>> allSettings = settingsControllerService.getAllSettings();
        if (allSettings.isEmpty()) {
            return ResponseDto.error(-1, "System Error");
        }
        return ResponseDto.successWithData(allSettings);
    }

    @GetMapping("/global")
    public ResponseDto<Map<String, String>> getGlobalSettings() {
        Map<String, String> globalSettings = settingsControllerService.getGlobalSettings();
        if (globalSettings.isEmpty()) {
            return ResponseDto.error(-1, "System Error");
        }
        return ResponseDto.successWithData(globalSettings);
    }

    @GetMapping("/custom")
    public ResponseDto<Map<String, String>> getCustomSettings() {
        return ResponseDto.successWithData(settingsControllerService.getCustomSettings());
    }

    @PostMapping("/global")
    public ResponseDto<Object> setGlobalSettings(@RequestBody Map<String, String> globalSettings) {
        Boolean status = settingsControllerService.setGlobalSettings(globalSettings);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Update failed");
        }
    }

    @PostMapping("/custom")
    public ResponseDto<Object> setCustomSettings(@RequestBody Map<String, String> customSettings) {
        Boolean status = settingsControllerService.setCustomSettings(customSettings);
        if (status) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error(-1, "Update failed");
        }
    }
}
