package live.vortox.vortoxlifesteal.listeners;

import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import live.vortox.vortoxlifesteal.utils.PlayerStorage;
import live.vortox.vortoxlifesteal.utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.List;

public class PlayerJoinListener implements Listener {

    private final VortoxLifeSteal plugin;

    public PlayerJoinListener(VortoxLifeSteal plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        List<PlayerStorage> playerList;

        try {
            playerList = StorageUtil.returnPlayerList();
        } catch (IOException e) {
            Bukkit.getLogger().warning("Could not return player list.");
            return;
        }

        PlayerStorage storedPlayer = StorageUtil.findPlayer(playerList, player);

        if (storedPlayer == null) {
            try {
                StorageUtil.addPlayer(player);
            } catch (IOException e) {
                Bukkit.getLogger().warning("Unable to add " + player.getName() + " in storage.");
            }
        }

        if (storedPlayer.isEliminated() && !plugin.getConfig().getString("elimination-type").equalsIgnoreCase("spectator")) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(storedPlayer.getRevivalHearts() * 2);

            try {
                StorageUtil.updatePlayer(player, "eliminated", "false");
            } catch (IOException e) {
                Bukkit.getLogger().warning("Unable to uneliminate " + player.getName() + " in storage.");
            }

            player.sendMessage(ChatColor.GREEN + "You have been unbanned and revived!");

        }

        if (!storedPlayer.getUsername().equals(player.getName())) {
            try {
                StorageUtil.updatePlayer(player, "username", player.getName());
            } catch (IOException e) {
                Bukkit.getLogger().warning("Unable to set " + player.getName() + " in storage.");
            }
        }

    }
}
