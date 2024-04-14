package io.lightplugins.pixel.regen.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.regen.abstracts.RegenAbstract;
import io.lightplugins.pixel.util.hooks.WorldGuardHook;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        if(!query.testState(BukkitAdapter.adapt(player.getLocation()), localPlayer, WorldGuardHook.LIGHT_REGEN)) {
            return;
        }

        if(player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        List<Material> materials = Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOTS);
        Location location = event.getBlock().getLocation();

        if(materials.contains(event.getBlock().getType())) {
            RegenAbstract regenAbstract = new RegenAbstract();
            regenAbstract.setType(event.getBlock());
            regenAbstract.setLocation(location);
            regenAbstract.setTimer(10);
            startTimer(regenAbstract);
        }
    }

    private void startTimer(RegenAbstract regenAbstract) {

        int timer = regenAbstract.getTimer();   // in seconds
        Location location = regenAbstract.getLocation();    //  the target block location
        Block block = regenAbstract.getType();    // the target block type
        Material material = block.getType();

        new BukkitRunnable() {
            @Override
            public void run() {

                location.getBlock().setType(material);

                BlockData bd = block.getBlockData();

                if(bd instanceof Ageable ageable) {

                    switch (block.getType()) {
                        case WHEAT, CARROTS, POTATOES -> ageable.setAge(7);
                        case BEETROOTS -> ageable.setAge(3);
                    }

                    location.getBlock().setBlockData(bd);
                    location.getBlock().getState().update(true);
                }
            }
        }.runTaskLater(Light.instance, timer * 20L);
    }
}
