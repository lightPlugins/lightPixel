package io.lightplugins.pixel.compactor.models;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class Compactor {

    private boolean isActive = false;   // is the compactor active or not
    private UUID compactorID;     // unique ID for the compactor
    private ItemStack compactorItem;    // the item that is the compactor
    private List<ItemStack> itemsToCompact;    // coming from ecoItems like enchanted Wheat etc ...
    private int maxItemsToCompact;  // how many items can be put in the compactor. capped by permission -> unlocks by skills
    private int currentItemsToCompact;  // how many items are currently in the compactor

    public Compactor(UUID compactorID, ItemStack compactorItem, List<ItemStack> itemsToCompact, int maxItemsToCompact) {
        this.compactorID = compactorID;
        this.itemsToCompact = itemsToCompact;
        this.maxItemsToCompact = maxItemsToCompact;
    }

    public ItemStack getCompactorItem() {
        return compactorItem;
    }

    public void setCompactorItem(ItemStack compactorItem) {
        this.compactorItem = compactorItem;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UUID getCompactorID() {
        return compactorID;
    }

    public void setCompactorID(UUID compactorID) {
        this.compactorID = compactorID;
    }

    public List<ItemStack> getItemsToCompact() {
        return itemsToCompact;
    }

    public void setItemsToCompact(List<ItemStack> itemsToCompact) {
        this.itemsToCompact = itemsToCompact;
    }

    public int getMaxItemsToCompact() {
        return maxItemsToCompact;
    }

    public void setMaxItemsToCompact(int maxItemsToCompact) {
        this.maxItemsToCompact = maxItemsToCompact;
    }

    public int getCurrentItemsToCompact() {
        return currentItemsToCompact;
    }

    public void setCurrentItemsToCompact(int currentItemsToCompact) {
        this.currentItemsToCompact = currentItemsToCompact;
    }

    public void openInventory() {
        // open the inventory for the player
    }


}
