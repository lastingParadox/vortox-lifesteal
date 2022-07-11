package live.vortox.vortoxlifesteal.listeners;

import live.vortox.vortoxlifesteal.utils.StorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;


public class BannedPlayerListener implements Listener {
    @EventHandler
    public void onBanJoin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_BANNED) {
            if(StorageUtil.findPlayer(event.getPlayer()).isEliminated())
                event.setKickMessage(ChatColor.RED + "You died and lost all of your max health!");

        }
    }
}
