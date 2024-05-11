package io.lightplugins.pixel.profiles.api;

import io.lightplugins.pixel.profiles.models.Profile;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LightProfilesAPI {

    public ConcurrentHashMap<UUID, Profile> playerProfiles = new ConcurrentHashMap<>();

    public Profile getPlayerProfile(UUID playerUUID) {
        return this.playerProfiles.get(playerUUID);
    }

}
