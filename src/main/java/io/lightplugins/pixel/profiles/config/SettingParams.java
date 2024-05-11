package io.lightplugins.pixel.profiles.config;

import io.lightplugins.pixel.compactor.LightCompactor;
import io.lightplugins.pixel.profiles.LightProfiles;

public class SettingParams {

    private final LightProfiles lightProfiles;
    private final String defaultCurrency = "default-currency.";

    public SettingParams(LightProfiles lightProfiles) {
        this.lightProfiles = lightProfiles;
    }

    public String getModuleLanguage() {
        return lightProfiles.getSettings().getConfig().getString("module-language");
    }
}
