package cn.luijp.escserver.service.impl;

import cn.luijp.escserver.model.entity.CustomSettings;
import cn.luijp.escserver.model.entity.GlobalSettings;
import cn.luijp.escserver.service.ICustomSettingsService;
import cn.luijp.escserver.service.IGlobalSettingsService;
import cn.luijp.escserver.service.SettingsControllerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettingsControllerServiceImpl implements SettingsControllerService {

    private final IGlobalSettingsService globalSettingsService;

    private final ICustomSettingsService customSettingsService;

    @Autowired
    public SettingsControllerServiceImpl(IGlobalSettingsService globalSettingsService,
                                         ICustomSettingsService customSettingsService){
        this.globalSettingsService = globalSettingsService;
        this.customSettingsService = customSettingsService;
    }

    public Map<String, Map<String, String>> getAllSettings() {
        Map<String, Map<String, String>> allSettingsMap = new HashMap<>();
        allSettingsMap.put("globalSettings", getGlobalSettings());
        allSettingsMap.put("customSettings", getCustomSettings());
        return allSettingsMap;
    }

    public Map<String, String> getGlobalSettings() {
        Map<String, String> globalSettingsMap = new HashMap<>();
        List<GlobalSettings> globalSettingsList = globalSettingsService.list();
        globalSettingsList.forEach(item -> globalSettingsMap.put(item.getK(), item.getV()));
        return globalSettingsMap;
    }

    public Boolean setGlobalSettings(Map<String, String> globalSettings) {
        List<GlobalSettings> globalSettingsUpdateList = new ArrayList<>();
        globalSettings.forEach((k, v) -> {
            GlobalSettings globalSettingsItem = new GlobalSettings();
            globalSettingsItem.setK(k);
            globalSettingsItem.setV(v);
            globalSettingsUpdateList.add(globalSettingsItem);
        });
        return globalSettingsService.saveOrUpdateBatch(globalSettingsUpdateList);

    }

    public Map<String, String> getCustomSettings() {
        Map<String, String> customSettingsMap = new HashMap<>();
        List<CustomSettings> customSettingsList = customSettingsService.list();
        customSettingsList.forEach(item -> customSettingsMap.put(item.getK(), item.getV()));
        return customSettingsMap;
    }

    public Boolean setCustomSettings(Map<String, String> customSettings) {
        List<CustomSettings> customSettingsUpdateList = new ArrayList<>();
        List<String> customSettingsDelList = new ArrayList<>();
        customSettings.forEach((k, v) -> {
            if (Objects.equals(v, "")) {
                customSettingsDelList.add(k);
            } else {
                CustomSettings customSettingsItem = new CustomSettings();
                customSettingsItem.setK(k);
                customSettingsItem.setV(v);
                customSettingsUpdateList.add(customSettingsItem);
            }
        });
        return deleteCustomSettings(customSettingsDelList) && customSettingsService.saveOrUpdateBatch(customSettingsUpdateList);
    }

    private Boolean deleteCustomSettings(List<String> customSettingsDelList) {
        if(!customSettingsDelList.isEmpty()){
            QueryWrapper<GlobalSettings> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("k",customSettingsDelList);
            return globalSettingsService.remove(queryWrapper);
        }
        return true;
    }
}
