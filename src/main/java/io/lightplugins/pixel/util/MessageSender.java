package io.lightplugins.pixel.util;
import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.skills.LightSkills;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageSender {

    public void sendPlayerMessage(String message, Player player) {
        Bukkit.getScheduler().runTask(Light.instance, () -> {
            Audience audience = (Audience) player;
            Component component = Light.instance.colorTranslation.universalColor(LightSkills.getMessageParams().prefix() + message, player);
            audience.sendMessage(component);
        });
    }
}
