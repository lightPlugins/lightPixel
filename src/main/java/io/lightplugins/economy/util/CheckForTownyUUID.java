package io.lightplugins.economy.util;

import com.palmergames.bukkit.towny.TownyEconomyHandler;

import java.util.UUID;

public class CheckForTownyUUID {

    public static UUID getTownyUUID(String account) {
        return TownyEconomyHandler.getTownyObjectUUID(account);
    }

}
