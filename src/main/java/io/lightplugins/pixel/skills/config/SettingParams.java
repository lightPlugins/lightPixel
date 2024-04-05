package io.lightplugins.pixel.skills.config;

import io.lightplugins.pixel.skills.LightSkills;
import io.lightplugins.pixel.util.NumberFormatter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class SettingParams {

    private final LightSkills lightSkills;
    private final String defaultCurrency = "default-currency.";

    public SettingParams(LightSkills lightSkills) {
        this.lightSkills = lightSkills;
    }



    public String getModuleLanguage() {
        return lightSkills.getSettings().getConfig().getString("module-language");
    }



    public DefaultCurrency defaultCurrency() {
        return new DefaultCurrency();
    }
    public SettingWrapper mainSettings() {
        return new SettingWrapper();
    }


    public class SettingWrapper {
        public SimpleDateFormat getDateFormat() {
            String result = lightSkills.getSettings().getConfig().getString("module-language");
            return result != null ? new SimpleDateFormat(result) : new SimpleDateFormat("dd:MM:yyyy");
        }
    }


    public class DefaultCurrency {
        public BigDecimal getStartBalance() {
            double startBalance = lightSkills.getSettings().getConfig().getDouble(defaultCurrency + "start-balance");
            return NumberFormatter.formatBigDecimal(BigDecimal.valueOf(startBalance));
        }
        public boolean firstJoinMessageEnabled() {
            return lightSkills.getSettings().getConfig().getBoolean(defaultCurrency + "enable-first-join-message");
        }
        public String currencyPluralName() {
            return lightSkills.getSettings().getConfig().getString(defaultCurrency + "currency-name-plural");
        }
        public String currencySingularName() {
            return lightSkills.getSettings().getConfig().getString(defaultCurrency + "currency-name-singular");
        }
        public Integer fractionalDigits() {
            return lightSkills.getSettings().getConfig().getInt(defaultCurrency + "fractional-digits");
        }
        public double maxPocketBalance() {
            return lightSkills.getSettings().getConfig().getDouble(defaultCurrency + "max-pocket-balance");
        }
    }
}
