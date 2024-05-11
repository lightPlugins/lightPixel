package io.lightplugins.pixel.compactor.events;

import com.willfp.ecoitems.items.EcoItem;
import com.willfp.ecoitems.items.EcoItems;
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

        EcoItem compactor2000 = EcoItems.INSTANCE.getByID("super_compactor_2000");
        EcoItem compactor4000 = EcoItems.INSTANCE.getByID("super_compactor_4000");
        EcoItem compactor6000 = EcoItems.INSTANCE.getByID("super_compactor_6000");

        ItemStack test = compactor2000.getItemStack();

        if(compactor2000 == null || compactor4000 == null || compactor6000 == null) {
            return;
        }

        if(itemStack == null) {
            return;
        }

        if(itemStack.equals(compactor2000.getItemStack())) {

            //  TODO -> PlayerProfile storage in database and gets compactors from there

            LightCompactor.compactor2000.get(player.getUniqueId());

        }
    }

}
