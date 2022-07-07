package live.vortox.vortoxlifesteal.listeners;

import live.vortox.vortoxlifesteal.items.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class PreventHeartEnderListener implements Listener {
    @EventHandler
    public void onPlayerDeath(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.ENDER_CHEST) {

            ItemStack selectedItem;
            if (event.getClick() == ClickType.NUMBER_KEY)
                selectedItem = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
            else
                selectedItem = event.getCursor();

            if (selectedItem != null && selectedItem.getItemMeta().equals(ItemManager.heart)) {
                event.setCancelled(true);
            }
        }
    }
}
