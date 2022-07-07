package live.vortox.vortoxlifesteal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DonateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final String example = ChatColor.RED + "Example: /donate <player> <integer>";
            Player player = (Player) sender;
            double max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

            //In case user tries to submit "/donate" without arguments.
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You need to provide the player to donate to!");
                player.sendMessage(example);
            }
            //In case user tries to submit "/donate" without an amount provided.
            else if (args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need to provide the the amount of hearts you are donating!");
                player.sendMessage(example);
                return true;
            }
            else {
                //Get the target, if they don't exist or are the player activating the command, terminate.
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist or is not online!");
                    return true;
                }
                if (target == player) {
                    player.sendMessage(ChatColor.RED + "You cannot donate hearts to yourself!");
                    return true;
                }

                //Try to convert the amount to a positive integer. If not possible, terminate.
                int amount;
                try {
                    amount = Math.abs(Integer.parseInt(args[1]));
                    amount *= 2;
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Amount provided is not an integer number of hearts.");
                    player.sendMessage(example);
                    return true;
                }

                //Terminate if user does not have enough health.
                if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - amount <= 0) {
                    player.sendMessage(ChatColor.RED + "You do not have enough hearts to donate that many!");
                    return true;
                }

                //Subtract from player, add to target.
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max - amount);

                double targetMax = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(targetMax + amount);

                player.sendMessage(ChatColor.GREEN + "You successfully gave " + amount/2 + " hearts to " + target.getName() + "!");
                target.sendMessage(ChatColor.GREEN + "You received " + amount/2 + " hearts from " + player.getName() + "!");

                //If target was previously dead, revive them.
                if (targetMax == 0) {
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(ChatColor.GREEN + player.getName() + " has revived you! You are now in survival.");
                }
            }
        }
        return true;
    }

}
