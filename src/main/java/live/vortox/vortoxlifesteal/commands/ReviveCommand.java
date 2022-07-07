package live.vortox.vortoxlifesteal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final String example = ChatColor.RED + "Example: /donate <player> <integer>";
            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You need to provide the player to revive!");
                player.sendMessage(example);
            }

            else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist or is not online!");
                    return true;
                }
                double targetMax = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                if (targetMax > 0) {
                    if (target == player)
                        player.sendMessage(ChatColor.RED + " You cannot be revived, you are alive!");
                    else
                        player.sendMessage(ChatColor.RED + target.getName() + " cannot be revived, they are alive!");
                    return true;
                }
                else {
                    if (args[1] != null) {
                        int amount;
                        try {
                            amount = Math.abs(Integer.parseInt(args[1]));
                            amount *= 2;
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Amount provided is not an integer number of hearts.");
                            player.sendMessage(example);
                            return true;
                        }
                        target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount);
                    }
                    else
                        target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2);

                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(ChatColor.GREEN + "You have been revived! You are now in survival.");
                }

            }
        }
        return true;
    }
}
