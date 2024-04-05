package io.lightplugins.pixel.bank.config;

import io.lightplugins.pixel.bank.LightBank;

public class SettingParams {

    private final LightBank lightBank;

    public SettingParams(LightBank lightBank) {
        this.lightBank = lightBank;
    }

    public String getModuleLanguage() {
        return lightBank.getSettings().getString("module-language");
    }
}
