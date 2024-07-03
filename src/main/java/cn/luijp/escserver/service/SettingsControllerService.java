package cn.luijp.escserver.service;

import java.util.Map;

public interface SettingsControllerService {
    Map<String, Map<String, String>> getAllSettings();

    Map<String, String> getGlobalSettings();

    Boolean setGlobalSettings(Map<String, String> globalSettings);

    Map<String, String> getCustomSettings();

    Boolean setCustomSettings(Map<String, String> customSettings);

}
