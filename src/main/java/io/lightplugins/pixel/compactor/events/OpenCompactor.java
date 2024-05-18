package io.lightplugins.pixel.compactor.events;

import com.willfp.eco.core.items.Items;
import com.willfp.eco.core.items.TestableItem;
import io.lightplugins.pixel.compactor.LightCompactor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OpenCompactor implements Listener {

    @EventHandler
    public void onRightClickCompactor(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        ItemStack itemStack = event.getItem();

        TestableItem compactor2000 = Items.lookup("super_compactor_2000");
        TestableItem compactor4000 = Items.lookup("super_compactor_4000");
        TestableItem compactor6000 = Items.lookup("super_compactor_6000");

        if(itemStack == null) {
            return;
        }

        if(itemStack.equals(compactor2000.getItem())) {

            //  TODO -> PlayerProfile storage in database and gets compactors from there

            LightCompactor.compactor2000.get(player.getUniqueId());

        }
    }

}
