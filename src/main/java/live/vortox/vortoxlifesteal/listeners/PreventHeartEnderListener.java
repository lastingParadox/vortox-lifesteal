package live.vortox.vortoxlifesteal.listeners;

import live.vortox.vortoxlifesteal.items.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PreventHeartEnderListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClick().isShiftClick()) {
            Inventory clicked = event.getClickedInventory();
            if (clicked == event.getWhoClicked().getInventory() && event.getView().getTopInventory().getType() == InventoryType.ENDER_CHEST) {
                ItemStack clickedOn = event.getCurrentItem();

                if (clickedOn != null && clickedOn.getItemMeta().equals(ItemManager.heart.getItemMeta())) {
                    event.setCancelled(true);
                }
            }
        }
        else if (event.getClickedInventory().getType() == InventoryType.ENDER_CHEST) {
            Inventory clicked = event.getClickedInventory();
            ItemStack selectedItem;

            if (clicked != event.getWhoClicked().getInventory()) {
                if (event.getClick() == ClickType.NUMBER_KEY)
                    selectedItem = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
                else
                    selectedItem = event.getCursor();

                if (selectedItem != null && selectedItem.hasItemMeta() && selectedItem.getItemMeta().equals(ItemManager.heart.getItemMeta()))
                    event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {

        if (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
            ItemStack dragged = event.getOldCursor();

            if (dragged.getItemMeta().equals(ItemManager.heart.getItemMeta())) {
                int inventorySize = event.getInventory().getSize();

                for (int i : event.getRawSlots()) {
                    if (i < inventorySize) {
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }
}
