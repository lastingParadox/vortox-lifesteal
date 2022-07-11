package live.vortox.vortoxlifesteal.commands;

import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import live.vortox.vortoxlifesteal.utils.ElimUtil;
import live.vortox.vortoxlifesteal.utils.PlayerStorage;
import live.vortox.vortoxlifesteal.utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand implements CommandExecutor {

    private final VortoxLifeSteal plugin;

    public ReviveCommand(VortoxLifeSteal plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final String example = ChatColor.RED + "Example: /revive <player> [integer]";
            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You need to provide the player to revive!");
                player.sendMessage(example);

            }
            else {
                String eliminationType = plugin.getConfig().getString("elimination-type");

                double amount = 0;
                if (args.length >= 2) {
                    try {
                        amount = Math.abs(Integer.parseInt(args[1]));
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + args[1] + " is not an integer amount of hearts!");
                        return true;
                    }
                }

                if (eliminationType.equalsIgnoreCase("spectator")) {

                    Player target = Bukkit.getPlayerExact(args[0]);

                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist or is not online!");
                        return true;
                    } else if (target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() > 0) {
                        player.sendMessage(ChatColor.RED + target.getName() + " cannot be revived, as they are not eliminated!");
                    }

                    ElimUtil.revivePlayer(target, amount);

                    player.sendMessage(ChatColor.GREEN + "Revived " + target.getName() + " with "
                            + (amount > 0 ? (int) amount + " hearts!" : "1 heart!"));
                }
                else {
                    OfflinePlayer target;
                    PlayerStorage temp = StorageUtil.findPlayer(args[0]);

                    if (temp == null) {
                        player.sendMessage(ChatColor.RED + "The provided player does not exist!");
                        return true;
                    }

                    target = Bukkit.getOfflinePlayer(temp.getUuid());

                    ElimUtil.revivePlayer(target, amount);

                    player.sendMessage(ChatColor.GREEN + "Revived " + target.getName() + " with "
                            + (amount > 0 ? (int) amount + " hearts!" : "1 heart!"));
                }
            }
        }
        return true;
    }

}

