package live.vortox.vortoxlifesteal.utils;

import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ElimUtil {

    public static VortoxLifeSteal plugin;

    public static void eliminatePlayer(Player player) {
        String eliminationType = plugin.getConfig().getString("elimination-type");
        try {
            StorageUtil.updatePlayer(player, "eliminated", "true");
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to update " + player.getName() + "'s eliminated status in storage.");
        }

        if (eliminationType.equalsIgnoreCase("spectator")) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.RED + "You lost all of your max health! You will be a "
                    + "spectator until someone revives you.");
        }
        else {
            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(),
                    "You died and lost all of your max health!", null, "");
        }
    }

    public static void revivePlayer(Player player, double amount) {

        if (amount == 0)
            amount = 1;

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount * 2);

        try {
            StorageUtil.updatePlayer(player, "hearts", String.valueOf(amount));
            StorageUtil.updatePlayer(player, "eliminated", "false");

        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to update " + player.getName() + "'s eliminated status in storage.");
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(ChatColor.GREEN + "You have been revived! You are now in survival.");

    }

    public static void revivePlayer(OfflinePlayer player, double amount) {
        if (amount == 0)
            amount = 1;

        try {
            StorageUtil.updatePlayer((Player) player, "revivalHearts", String.valueOf(amount));
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to revive " + player.getName() + " in storage.");
        }
    }
}
