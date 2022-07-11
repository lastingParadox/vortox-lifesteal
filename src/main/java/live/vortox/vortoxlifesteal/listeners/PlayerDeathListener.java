package live.vortox.vortoxlifesteal.listeners;

import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import live.vortox.vortoxlifesteal.items.ItemManager;
import live.vortox.vortoxlifesteal.utils.ElimUtil;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final VortoxLifeSteal plugin;

    public PlayerDeathListener(VortoxLifeSteal plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        try {
            //Reduce player's health by 1 heart.
            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth - 2);

            //In the case another player kills, give them automatic health
            if (event.getEntity().getKiller() != null && plugin.getConfig().getBoolean("automatic-health")) {
                double killerMaxHealth = event.getEntity().getKiller().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                event.getEntity().getKiller().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killerMaxHealth + 2);
            }
            //If automatic health is disabled, drop a heart on death from another player.
            else if (event.getEntity().getKiller() != null) {
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), ItemManager.heart);
            }

            //In the case the player is at 0 max hp
            if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0) {

                ElimUtil.eliminatePlayer(player);
            }
        } catch (NullPointerException e) {
            Bukkit.getLogger().warning("\u001B[31mPlayer does not exist!\u001B[0m");
            e.printStackTrace();
        }
    }


}
