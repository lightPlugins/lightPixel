package io.lightplugins.pixel.util.events;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.jeff_media.customblockdata.CustomBlockData;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.util.Multiplier;
import io.lightplugins.pixel.util.NameSpaceKeys;
import io.lightplugins.pixel.util.hooks.WorldGuardHook;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.*;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AutoCollect implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Material mat = event.getBlock().getType();
        Block block = event.getBlock();

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        /*
         *  Check if the player is in a region that has the LIGHT_REGEN flag set to DENY
         *

        if(!query.testState(BukkitAdapter.adapt(player.getLocation()), localPlayer, WorldGuardHook.LIGHT_REGEN)) {
            return;
        }

         */

        boolean isLightRegenZone = query.testState(BukkitAdapter.adapt(player.getLocation()), localPlayer, WorldGuardHook.LIGHT_REGEN);

        if(Light.isSkyblockHook || isLightRegenZone) {

            SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player.getUniqueId());
            Island island = superiorPlayer.getIsland();

            if(!island.isMember(superiorPlayer)) {
                return;
            }
        }

        if(event.getBlock().getType().equals(Material.CHEST)) {
            return;
        }

        if(player.getInventory().firstEmpty() == -1) {
            player.sendTitle("§4Inventory full", "§cPlease clear some space", 0, 20, 15);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        //  check if the black was placed by a non-creative player

        Multiplier multiplier = new Multiplier(player);

        //  handle SUGAR_CANE

        if(mat.equals(Material.SUGAR_CANE)) {

            Block aboveBlock = event.getBlock().getRelative(0, 1, 0);

            while (aboveBlock.getType().equals(Material.SUGAR_CANE)) {
                ItemStack drop = new ItemStack(Material.SUGAR_CANE, isPlayerPlaced(aboveBlock) ? 1 : multiplier.getFarmingFortuneAmount());
                player.getInventory().addItem(drop);
                aboveBlock.setType(Material.AIR);
                aboveBlock = aboveBlock.getRelative(0, 1, 0);
            }
            event.setDropItems(false);
        }

        //  handle CACTUS

        if(mat.equals(Material.CACTUS)) {

            Block aboveBlock = event.getBlock().getRelative(0, 1, 0);

            while (aboveBlock.getType().equals(Material.CACTUS)) {
                ItemStack drop = new ItemStack(Material.CACTUS, isPlayerPlaced(aboveBlock) ? 1 : multiplier.getFarmingFortuneAmount());
                player.getInventory().addItem(drop);
                aboveBlock.setType(Material.AIR);
                aboveBlock = aboveBlock.getRelative(0, 1, 0);
            }
            event.setDropItems(false);
        }

        //  handle KELP

        if(mat.equals(Material.KELP)) {

            Block aboveBlock = event.getBlock().getRelative(0, 1, 0);

            while (aboveBlock.getType().equals(Material.KELP)) {
                ItemStack drop = new ItemStack(Material.KELP, isPlayerPlaced(aboveBlock) ? 1 : multiplier.getFarmingFortuneAmount());
                player.getInventory().addItem(drop);
                aboveBlock.setType(Material.AIR);
                aboveBlock = aboveBlock.getRelative(0, 1, 0);
            }
            event.setDropItems(false);
        }

        Collection<ItemStack> drops = event.getBlock().getDrops();

        //  handle all other drops from farming, mining and foraging

        drops.forEach(singleStack -> {

            //  handle farming materials

            List<Material> materialFarming = Arrays.asList(
                    Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOTS,
                    Material.SUGAR_CANE, Material.CACTUS, Material.KELP,
                    Material.MELON, Material.PUMPKIN, Material.SWEET_BERRIES);

            //  handle mining materials

            List<Material> materialMining = Arrays.asList(
                    Material.COAL,
                    Material.RAW_IRON,
                    Material.RAW_GOLD,
                    Material.DIAMOND,
                    Material.EMERALD,
                    Material.REDSTONE,
                    Material.LAPIS_LAZULI,
                    Material.RAW_COPPER,
                    Material.STONE,
                    Material.DEEPSLATE,
                    Material.COBBLESTONE);

            //  handle foraging materials

            List<Material> materialForaging = Arrays.asList(
                    Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG,
                    Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.MANGROVE_LOG,
                    Material.CRIMSON_STEM, Material.WARPED_STEM);

            List<Material> nonMultiplierDrops = Arrays.asList(
                    Material.WHEAT_SEEDS, Material.BEETROOT_SEEDS, Material.MELON_SEEDS,
                    Material.PUMPKIN_SEEDS, Material.TORCHFLOWER_SEEDS);

            //  handle multiplier for farming materials

            if(block.getBlockData() instanceof Ageable ageable) {
                if(materialFarming.contains(singleStack.getType())) {
                    if(!isPlayerPlaced(block) || ageable.getAge() == ageable.getMaximumAge()) {
                        singleStack.setAmount(Math.min(singleStack.getAmount() * multiplier.getFarmingFortuneAmount(), 64));
                    }
                }
            } else {
                if(materialFarming.contains(singleStack.getType())) {
                    if(!isPlayerPlaced(block)) {
                        singleStack.setAmount(Math.min(singleStack.getAmount() * multiplier.getFarmingFortuneAmount(), 64));
                    }
                }
            }


            //  handle multiplier for mining materials

            if(materialMining.contains(singleStack.getType())) {
                if(!isPlayerPlaced(block)) {
                    singleStack.setAmount(Math.min(singleStack.getAmount() * multiplier.getMiningFortuneAmount(), 64));
                }
            }

            //  handle multiplier for foraging materials

            if(materialForaging.contains(singleStack.getType())) {
                if(!isPlayerPlaced(block)) {
                    singleStack.setAmount(Math.min(singleStack.getAmount() * multiplier.getForagingFortuneAmount(), 64));
                }
            }

            //  handle non-multiplier drops (blacklist)

            if(nonMultiplierDrops.contains(singleStack.getType())) {
                singleStack.setAmount(singleStack.getAmount());
            }

            //  handle the get items method

            if(player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(singleStack);
            } else {
                player.getWorld().dropItemNaturally(event.getBlock().getLocation(), singleStack);
            }
        });

        event.setDropItems(false);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        //  create a PersistentDataContainer if the block was placed by a non-creative player.

        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        Block block = event.getBlock();
        PersistentDataContainer container = new CustomBlockData(block, Light.instance);
        NamespacedKey key = new NamespacedKey(Light.getPlugin(Light.class), NameSpaceKeys.PLAYER_PLACED.getType());
        container.set(key, PersistentDataType.STRING, event.getPlayer().getUniqueId().toString());
        block.getState().update();
    }

    public boolean isPlayerPlaced(Block block) {
        PersistentDataContainer dataContainer = new CustomBlockData(block, Light.instance);
        NamespacedKey key = new NamespacedKey(Light.getPlugin(Light.class), NameSpaceKeys.PLAYER_PLACED.getType());
        return dataContainer.has(key, PersistentDataType.STRING);
    }
}
