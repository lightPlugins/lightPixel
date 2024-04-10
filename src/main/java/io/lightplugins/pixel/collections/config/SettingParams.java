package io.lightplugins.pixel.collections.config;

import io.lightplugins.pixel.collections.LightCollections;
import io.lightplugins.pixel.regen.LightRegen;

public class SettingParams {

    private final LightCollections lightCollections;

    public SettingParams(LightCollections lightCollections) {
        this.lightCollections = lightCollections;
    }

    public String getModuleLanguage() {
        return lightCollections.getSettings().getConfig().getString("module-language");
    }


}
