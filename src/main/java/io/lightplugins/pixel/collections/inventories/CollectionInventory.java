package io.lightplugins.pixel.collections.inventories;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.collections.LightCollections;
import io.lightplugins.pixel.collections.models.ClickGuiStack;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.Objects;

public class CollectionInventory {

    private final ChestGui gui = new ChestGui(6, "Init");
    private final Player player;
    private final String category;
    private BukkitTask bukkitTask;

    public CollectionInventory(Player player, String category) {
        this.player = player;
        this.category = category;
    }

    public void openInventory() {

        String title = Light.instance.colorTranslation.loreLineTranslation(
                "<gray>Farming Collection", player);

        gui.setTitle(title);
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);
        gui.addPane(background);

        OutlinePane pane = new OutlinePane(1, 1, 5, 8);

        for(File file : LightCollections.instance.getCollectionByCategory(category)) {

            FileConfiguration conf = YamlConfiguration.loadConfiguration(file);

            String type = file.getName().replace(".yml", "");
            boolean isDiscovered = true;
            boolean isCompleted = false;
            boolean isUndiscovered = false;


            ItemStack itemStack = new ItemStack(Material.STONE, 1);

            ClickGuiStack closeButton = new ClickGuiStack(
                    Objects.requireNonNull(conf.getConfigurationSection(
                            "collection-menu.close-button")), player);

            ClickGuiStack backButton = new ClickGuiStack(
                    Objects.requireNonNull(conf.getConfigurationSection(
                            "collection-menu.back-button")), player);

            if (isDiscovered) {
                ClickGuiStack clickGuiStack = new ClickGuiStack(
                        Objects.requireNonNull(conf.getConfigurationSection(
                                "collection-menu.discovered")), player);
                itemStack = clickGuiStack.getGuiItem();
            }
            if (isCompleted) {
                ClickGuiStack clickGuiStack = new ClickGuiStack(
                        Objects.requireNonNull(conf.getConfigurationSection(
                                "collection-menu.maxed-out")), player);
                itemStack = clickGuiStack.getGuiItem();
            }
            if (isUndiscovered) {
                ClickGuiStack clickGuiStack = new ClickGuiStack(
                        Objects.requireNonNull(conf.getConfigurationSection(
                                "collection-menu.undiscovered")), player);
                itemStack = clickGuiStack.getGuiItem();
            }

            pane.addItem(new GuiItem(itemStack, inventoryClickEvent -> {

                if(Objects.equals(inventoryClickEvent.getCurrentItem(), closeButton.getGuiItem())) {
                    player.closeInventory();
                    return;
                }

                if(Objects.equals(inventoryClickEvent.getCurrentItem(), backButton.getGuiItem())) {
                    OverviewInventory overviewInventory = new OverviewInventory(player);
                    overviewInventory.openInventory();
                    return;
                }

                if(inventoryClickEvent.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
                    return;
                }
                player.sendMessage("§7Category §c" + category + "§7 clicked to item §c" + type);
            }));
        }

        gui.addPane(pane);
        gui.show(player);
    }
}
