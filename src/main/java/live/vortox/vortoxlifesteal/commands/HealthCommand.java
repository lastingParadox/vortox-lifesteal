package live.vortox.vortoxlifesteal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealthCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final String example = ChatColor.RED + "Example: /health <player> [add|set] [integer]";
            Player player = (Player) sender;

            if (args.length == 0) {
                double max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                player.sendMessage(ChatColor.WHITE + "You have " + ChatColor.GREEN + max/2
                        + ChatColor.WHITE + " hearts.");
                return true;
            }
            else {

                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist or is not online!");
                    return true;
                }

                double targetMax = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                if (args.length == 1) {
                    if (target == player)
                        player.sendMessage(ChatColor.WHITE + "You have " + ChatColor.GREEN + targetMax/2
                                + ChatColor.WHITE + " hearts.");
                    else
                        player.sendMessage(ChatColor.WHITE + target.getName() + " has " + ChatColor.GREEN + targetMax/2
                                + ChatColor.WHITE + " hearts.");
                    return true;
                }

                if (args[1].equals("add") || args[1].equals("set")) {
                    if (args[2] == null) {
                        player.sendMessage(ChatColor.RED + "You need to provide a heart amount!");
                        player.sendMessage(example);
                        return true;
                    }

                    int amount;
                    String provided = args[2];

                    try {
                        amount = Integer.parseInt(provided) * 2;
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + provided + " is not a valid number!");
                        player.sendMessage(example);
                        return true;
                    }

                    target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(targetMax + amount);

                    if (amount < 0)
                        player.sendMessage(ChatColor.GREEN + "Removed " + amount/2 + " hearts from " + target.getName() + ".");
                    else
                        player.sendMessage(ChatColor.GREEN + "Gave " + amount/2 + " hearts to " + target.getName() + ".");

                }
                else {
                    player.sendMessage(ChatColor.RED + "Invalid command" + args[1] + "!");
                    player.sendMessage(example);
                    return true;
                }
            }
        }
        return true;
    }

}
