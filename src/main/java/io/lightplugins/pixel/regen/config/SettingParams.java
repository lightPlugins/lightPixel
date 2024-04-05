package io.lightplugins.pixel.regen.config;

import io.lightplugins.pixel.regen.LightRegen;

public class SettingParams {

    private final LightRegen lightRegen;
    private final String defaultCurrency = "default-currency.";

    public SettingParams(LightRegen lightRegen) {
        this.lightRegen = lightRegen;
    }

    public String getModuleLanguage() {
        return lightRegen.getSettings().getConfig().getString("module-language");
    }


}
