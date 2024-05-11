package io.lightplugins.pixel.compactor.config;

import io.lightplugins.pixel.compactor.LightCompactor;
import io.lightplugins.pixel.regen.LightRegen;

public class SettingParams {

    private final LightCompactor lightCompactor;
    private final String defaultCurrency = "default-currency.";

    public SettingParams(LightCompactor lightCompactor) {
        this.lightCompactor = lightCompactor;
    }

    public String getModuleLanguage() {
        return lightCompactor.getSettings().getConfig().getString("module-language");
    }
}
