package io.lightplugins.pixel.util.events;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.skills.Skill;
import com.willfp.ecoskills.skills.Skills;
import io.lightplugins.pixel.Light;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CheckSkillRequirement implements Listener {

    //@EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {


        Skill skill = Skills.INSTANCE.getByID("farming");

        if(skill == null) {
            Light.getDebugPrinting().print("Skill not found!");
            return;
        }

        int farmingSkill = EcoSkillsAPI.getSkillLevel(event.getPlayer(), skill);
        Material targetType = event.getBlock().getType();

        if(targetType.equals(Material.WHEAT)) {

            if(farmingSkill < 2) {
                event.setCancelled(true);
            }
        }

        Light.getDebugPrinting().print("Material Type: " + targetType);
    }
}
