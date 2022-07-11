package live.vortox.vortoxlifesteal.listeners;

import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import live.vortox.vortoxlifesteal.utils.PlayerStorage;
import live.vortox.vortoxlifesteal.utils.StorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final VortoxLifeSteal plugin;

    public PlayerJoinListener(VortoxLifeSteal plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        PlayerStorage storedPlayer;

        storedPlayer = StorageUtil.findPlayer(player);

        if (storedPlayer == null) {
            StorageUtil.addPlayer(player);
            return;
        }

        if (storedPlayer.getRevivalHearts() > 0) {
            double originalMax = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            if (storedPlayer.isEliminated())
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(storedPlayer.getRevivalHearts() * 2);
            else {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(originalMax + storedPlayer.getRevivalHearts() * 2);
            }

            if (storedPlayer.isEliminated()) {
                if (plugin.getConfig().getString("elimination-type").equalsIgnoreCase("spectator")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.GREEN + "You have been revived! You are now in survival.");
                }
                else
                    player.sendMessage(ChatColor.GREEN + "You have been unbanned and revived!");
            }
            else {
                player.sendMessage("You have been donated " + (int) storedPlayer.getRevivalHearts() + " hearts while you were away!");
            }

            if (storedPlayer.isEliminated()) {
                StorageUtil.updatePlayer(storedPlayer, "eliminated", "false");
            }
            StorageUtil.updatePlayer(storedPlayer, "hearts", String.valueOf(originalMax + storedPlayer.getRevivalHearts()));
            StorageUtil.updatePlayer(storedPlayer, "revivalHearts", "0");


        }

        if (!storedPlayer.getUsername().equals(player.getName())) {
            StorageUtil.updatePlayer(player, "username", player.getName());
        }

    }
}
