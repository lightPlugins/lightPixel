package io.lightplugins.pixel.util.interfaces;

import java.io.IOException;

public interface LightModule {

    void enable() throws IOException;

    void disable();

    void reload();

    boolean isEnabled();

    String getName();
}
