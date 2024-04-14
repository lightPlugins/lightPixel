package io.lightplugins.pixel.util.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import io.lightplugins.pixel.util.hooks.WorldGuardHook;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class AutoCollect implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Material mat = event.getBlock().getType();

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        if(!query.testState(BukkitAdapter.adapt(player.getLocation()), localPlayer, WorldGuardHook.LIGHT_REGEN)) {
            return;
        }

        if(player.getInventory().firstEmpty() == -1) {
            player.sendTitle("§4Inventory full", "§cPlease clear some space", 0, 20, 15);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        if(event.getBlock().getType().equals(Material.CHEST)) {
            player.sendMessage("Found droppable item: " + mat);
            return;
        }


        //  handle SUGAR_CANE

        if(mat.equals(Material.SUGAR_CANE)) {

            int remaining = 0;
            Block aboveBlock = event.getBlock().getRelative(0, 1, 0);
            while (aboveBlock.getType().equals(Material.SUGAR_CANE)) {
                remaining++;
                aboveBlock.setType(Material.AIR);
                aboveBlock = aboveBlock.getRelative(0, 1, 0);
            }
            ItemStack drop = new ItemStack(Material.SUGAR_CANE, remaining);
            player.getInventory().addItem(drop);
            event.setDropItems(false);
        }

        Collection<ItemStack> drops = event.getBlock().getDrops();

        drops.forEach(singleStack -> {

            if(player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(singleStack);
            } else {
                player.getWorld().dropItemNaturally(event.getBlock().getLocation(), singleStack);
                player.sendMessage("drop item on ground.");
            }
        });

        event.setDropItems(false);

    }
}
