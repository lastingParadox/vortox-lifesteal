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
        //Right-clicking the heart event.
        if (event.getItem() != null && event.getItem().getItemMeta().equals((ItemManager.heart.getItemMeta()))) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                Player player = event.getPlayer();
                double health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                //If a shift-click, use all hearts in the slot and give player appropriate amount of hearts.
                if (player.isSneaking()) {
                    int amountItems = event.getItem().getAmount();
                    event.getItem().setAmount(0);
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health + (amountItems * 2));
                    player.sendMessage(ChatColor.DARK_PURPLE + "You absorb the " + amountItems + ChatColor.LIGHT_PURPLE
                            + " hearts" + ChatColor.DARK_PURPLE + "...");
                }
                //Otherwise, use one heart and give player one heart.
                else {
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health + 2);
                    player.sendMessage(ChatColor.DARK_PURPLE + "You absorb the " + ChatColor.LIGHT_PURPLE + "heart"
                            + ChatColor.DARK_PURPLE + "...");
                }
            }
        }
    }
}
