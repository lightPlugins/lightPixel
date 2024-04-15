package io.lightplugins.pixel.util;

public enum NameSpaceKeys {

    PLAYER_PLACED("player-placed"),
            ;

    private String type;
    NameSpaceKeys(String type) { this.type = type; }
    public String getType() {

        return type;
    }
}
