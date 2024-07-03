package cn.luijp.escserver.service;

import java.util.List;
import java.util.Map;

public interface SettingsControllerService {
    public Map<String, Map<String, String>> getAllSettings();

    public Map<String, String> getGlobalSettings();

    public Boolean setGlobalSettings(Map<String, String> globalSettings);

    public Map<String, String> getCustomSettings();

    public Boolean setCustomSettings(Map<String, String> customSettings);

}
