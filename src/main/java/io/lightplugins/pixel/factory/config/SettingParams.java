package io.lightplugins.pixel.factory.config;

import io.lightplugins.pixel.factory.LightFactory;

public class SettingParams {

    private final LightFactory lightProfiles;
    private final String defaultCurrency = "default-currency.";

    public SettingParams(LightFactory lightProfiles) {
        this.lightProfiles = lightProfiles;
    }

    public String getModuleLanguage() {
        return lightProfiles.getSettings().getConfig().getString("module-language");
    }
}
