package io.lightplugins.pixel.exceptions;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.util.interfaces.LightModule;

public class ModuleNotEnabledException extends Exception {

    public ModuleNotEnabledException(LightModule module) {
        super(Light.consolePrefix + "The Module §e" + module.getName() + "§r is not enabled");
    }
}
