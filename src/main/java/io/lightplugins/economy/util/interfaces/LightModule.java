package io.lightplugins.economy.util.interfaces;

public interface LightModule {

    void enable();

    void disable();

    void reload();

    boolean isEnabled();

    String getName();
}
