package io.lightplugins.pixel.factory.inventories;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.factory.LightFactory;
import io.lightplugins.pixel.factory.models.ClickGuiStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Objects;

public class MainInventory {

    private final ChestGui gui = new ChestGui(6, "Init");
    private final Player player;
    private BukkitTask bukkitTask;

    public MainInventory(Player player) {
        this.player = player;
    }

    public void openInventory() {

        FileConfiguration conf = LightFactory.instance.getMainMenu().getConfig();
        String title = Light.instance.colorTranslation.loreLineTranslation(conf.getString("gui-title"), player);

        gui.setTitle(title);
        gui.setOnGlobalClick(event -> event.setCancelled(true));


        if(conf.getBoolean("background-items")) {
            OutlinePane background = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOWEST);
            ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta itemMeta = blackGlass.getItemMeta();

            if(itemMeta != null) {
                itemMeta.setDisplayName("");
            }

            background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
            background.setRepeat(true);
            gui.addPane(background);
        }

        String[] patternList = conf.getStringList("pattern").toArray(new String[0]);
        Pattern pattern = new Pattern(patternList);
        PatternPane pane = new PatternPane(0, 0, 9, 6, pattern);

        for(String path : Objects.requireNonNull(conf.getConfigurationSection("contents")).getKeys(false)) {

            ClickGuiStack category = new ClickGuiStack(Objects.requireNonNull(
                    conf.getConfigurationSection("contents." + path)), player);

            ItemStack itemStack = category.getGuiItem();
            String patternID = category.getPatternID();
            List<String> actions = category.getActions();

            if (category.getGuiItem().getItemMeta() instanceof SkullMeta skullMeta) {
                PlayerProfile playerProfile = Bukkit.createPlayerProfile("LightningDesign");
                skullMeta.setOwnerProfile(playerProfile);
                category.getGuiItem().setItemMeta(skullMeta);
                itemStack.setItemMeta(skullMeta);
            }

            pane.bindItem(patternID.charAt(0), new GuiItem(itemStack, inventoryClickEvent -> {

                if(inventoryClickEvent.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
                    return;
                }

                for(String action : actions) {

                    if(action.contains(";")) {
                        String[] actionSplit = action.split(";");
                        if(actionSplit[0].equalsIgnoreCase("open")) {
                            //  open the provided inventory
                            player.sendMessage("§7Opening inventory §c" + actionSplit[1]);
                            CategoryInventory categoryInventory = new CategoryInventory(player, actionSplit[1]);
                            categoryInventory.openInventory();
                            return;
                        }
                        //  Unknown action aka TODO
                    }

                    if(action.equalsIgnoreCase("close")) {
                        player.closeInventory();
                        return;
                    }
                }

                String output = path.substring(0, 1).toUpperCase() + path.substring(1);
                player.sendMessage("§7Category §c" + output + "§7 clicked");

            }));
        }

        gui.addPane(pane);
        gui.show(player);

    }
}
