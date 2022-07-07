package live.vortox.vortoxlifesteal.listeners;

import live.vortox.vortoxlifesteal.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HeartUseListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        //Right-clicking the heart provides the player with one heart of health.
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getItemMeta().equals(ItemManager.heart.getItemMeta())) {
                Player player= event.getPlayer();
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                double health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health + 2);
                player.sendMessage(ChatColor.DARK_PURPLE + "You absorb the heart...");
            }
        }
    }
}
