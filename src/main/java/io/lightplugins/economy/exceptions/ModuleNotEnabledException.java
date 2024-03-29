package io.lightplugins.economy.exceptions;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.light.api.LightModule;

public class ModuleNotEnabledException extends Exception {

    public ModuleNotEnabledException(LightModule module) {
        super(LightEconomy.consolePrefix + "The Module §e" + module.getName() + "§r is not enabled");
    }
}
