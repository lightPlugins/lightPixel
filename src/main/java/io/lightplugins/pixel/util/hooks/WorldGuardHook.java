package io.lightplugins.pixel.util.hooks;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import io.lightplugins.pixel.Light;

public class WorldGuardHook {

    public static StateFlag LIGHT_REGEN;
    public final String regenFlag = "light-regen";

    public void setupCustomFlags() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        try {

            StateFlag flag = new StateFlag(regenFlag, false);
            Light.getDebugPrinting().print("Registering custom flag: §e" + regenFlag);
            registry.register(flag);
            LIGHT_REGEN = flag;

        } catch (FlagConflictException e) {

            Flag<?> existing = registry.get(regenFlag);

            if(existing instanceof StateFlag) {
                Light.getDebugPrinting().print("Second try to registering custom flag: " + existing);
                LIGHT_REGEN = (StateFlag) existing;
            } else {
                throw new RuntimeException("Failed to register custom flags", e);
            }
        }
    }
}
