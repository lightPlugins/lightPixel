package io.lightplugins.pixel.util.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class AutoCollect implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        Material mat = event.getBlock().getType();

        if(player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setDropItems(false);
            return;
        }

        if(mat.equals(Material.KELP)
                || mat.equals(Material.KELP_PLANT)
                || mat.equals(Material.CHEST)
                || mat.equals(Material.BAMBOO)
                || mat.equals(Material.CACTUS)
                || mat.equals(Material.SUGAR_CANE)) {
            return;

            // TODO: Handle kelp, chests, bamboos, and cacti more efficiently because there can be growing up
            // For now let's just disable these for now
            // Maybe falling sand/gravel with torch could performance issues.

        }

        if(player.getInventory().firstEmpty() == -1) {
            player.sendTitle("§4INV is full", "§cPlease clear some space", 5, 20, 15);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            event.setCancelled(true);
        }

    }


    @EventHandler
    public void onBlockDrops(BlockDropItemEvent event) {

        Player player = event.getPlayer();

        player.sendMessage("get block: " + event.getBlock());

        // Due to some issues with dropping items from broken chests.
        // Drop items from a broken chest naturally.
        // TODO: THIS IS NOT WORKING. Maybe add the checks below to the BlocBreakEvent ...
        //       event.getBlock().getType() is AIR
        if(event.getBlock().getType().equals(Material.CHEST)) {
            return;
        }

        if(player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        List<Item> drops = event.getItems();

        for (Item dropedItem : drops) {

            ItemStack drop = dropedItem.getItemStack();
            Material mat = drop.getType();

            if(mat.equals(Material.KELP)
                    || mat.equals(Material.KELP_PLANT)
                    || mat.equals(Material.BAMBOO)
                    || mat.equals(Material.CACTUS)
                    || mat.equals(Material.SUGAR_CANE)) {

                return;

                // TODO: Handle kelp, chests, bamboos, and cacti more efficiently because there can be growing up
                // For now let's just disable these for now
                // Maybe falling sand/gravel with torch could performance issues.

            }


            HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(drop);
            if(leftOver.keySet().size() > 0) {

                if(player.getInventory().firstEmpty() == -1) {
                    player.sendTitle("§4INV is full", "§cPlease clear some space", 5, 20, 15);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                }
            }
        }
        // Cancel event to prevent dropping items (duplicates)
        event.setCancelled(true);


    }
}
