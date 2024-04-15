package io.lightplugins.pixel.collections.inventories;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.collections.LightCollections;
import io.lightplugins.pixel.collections.abstracts.Category;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class OverviewInventory {

    private final ChestGui gui = new ChestGui(6, "Init");
    private final Player player;
    private BukkitTask bukkitTask;

    public OverviewInventory(Player player) {
        this.player = player;
    }

    public void openInventory() {

        FileConfiguration conf = LightCollections.instance.getCategories().getConfig();
        String title = Light.instance.colorTranslation.loreLineTranslation(conf.getString("gui-title"), player);

        gui.setTitle(title);
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);
        String[] patternList = conf.getStringList("pattern").toArray(new String[0]);
        Pattern pattern = new Pattern(patternList);
        PatternPane pane = new PatternPane(0, 0, 9, 6, pattern);

        for(String path : Objects.requireNonNull(conf.getConfigurationSection(
                "contents.categories")).getKeys(false)) {

            Category category = new Category(Objects.requireNonNull(
                    conf.getConfigurationSection(
                            "contents.categories." + path)), player);
            ItemStack itemStack = category.getGuiItem();

            String patternID = category.getPatternID();

            if (category.getGuiItem().getItemMeta() instanceof SkullMeta skullMeta) {
                PlayerProfile playerProfile = Bukkit.createPlayerProfile("Beampur");
                skullMeta.setOwnerProfile(playerProfile);
                category.getGuiItem().setItemMeta(skullMeta);
                itemStack.setItemMeta(skullMeta);
            }

            pane.bindItem(patternID.charAt(0), new GuiItem(itemStack, inventoryClickEvent -> {
                String output = path.substring(0, 1).toUpperCase() + path.substring(1);
                player.sendMessage("§7Category §c" + output + "§7 clicked");

            }));

        }
        gui.addPane(pane);
        gui.show(player);
    }
}
