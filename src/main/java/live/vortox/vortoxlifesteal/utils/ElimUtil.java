package live.vortox.vortoxlifesteal.utils;

import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class ElimUtil {

    public static final String ELIMINATION_TYPE = VortoxLifeSteal.getPlugin().getConfig().getString("elimination-type");

    public static void eliminatePlayer(Player player) {
        StorageUtil.updatePlayer(player, "eliminated", "true");

        if (ELIMINATION_TYPE.equalsIgnoreCase("spectator")) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.RED + "You lost all of your max health! You will be a "
                    + "spectator until someone revives you.");
        }
        else {
            if (ELIMINATION_TYPE.equalsIgnoreCase("hardcore")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.RED + "You lost all of your max health! You have been banished, but"
                        + " you may roam this realm as a spectator until you disconnect.");
            }
            else
                player.kickPlayer(ChatColor.RED + "You died and lost all of your max health!");
            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(),
                    ChatColor.RED + "You died and lost all of your max health!", null, "");
        }
    }

    //Only works in the case of an online spectator.
    public static void revivePlayer(Player player, double amount) {

        if (amount == 0)
            amount = 1;

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount * 2);

        PlayerStorage temp = StorageUtil.findPlayer(player);
        StorageUtil.updatePlayer(temp, "hearts", String.valueOf(amount));
        StorageUtil.updatePlayer(temp, "eliminated", "false");
        StorageUtil.updatePlayer(temp, "revivalHearts", "0");

        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(ChatColor.GREEN + "You have been revived! You are now in survival.");

    }

    public static void revivePlayer(OfflinePlayer player, double amount) {
        if (amount == 0)
            amount = 1;

        StorageUtil.updatePlayer(player, "revivalHearts", String.valueOf(amount));

        if (ELIMINATION_TYPE.equalsIgnoreCase("banned")) {
            Bukkit.getBanList(BanList.Type.NAME).pardon(player.getName());
        }

    }
}
