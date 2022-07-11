package live.vortox.vortoxlifesteal.commands;

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

public class DonateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final String example = ChatColor.RED + "Example: /donate <player> <integer>";
            Player player = (Player) sender;
            double max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

            if (args.length < 2) {
                //In case user tries to submit "/donate" without arguments.
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "You need to provide the player to donate to!");
                }
                //In case user tries to submit "/donate" without an amount provided.
                else {
                    player.sendMessage(ChatColor.RED + "You need to provide the the amount of hearts you are donating!");
                }

                player.sendMessage(example);
                return true;
            }
            else {
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

                //Get the target, if they don't exist, donate to offline player.
                Player target = Bukkit.getPlayerExact(args[0]);

                if (target == null) {
                    PlayerStorage temp = StorageUtil.findPlayer(args[0]);

                    if (temp == null) {
                        player.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist or is not online!");
                        return true;
                    }

                    OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(temp.getUuid());

                    if (temp.isEliminated()) {
                        ElimUtil.revivePlayer(offlineTarget, (double) (amount)/2);
                    }
                    else {
                        StorageUtil.updatePlayer(offlineTarget, "revivalHearts", String.valueOf(amount/2));
                    }

                    player.sendMessage(ChatColor.GREEN + "You successfully gave " + amount/2 + " hearts to "
                            + args[0] + "! They will receive it when they're next online.");

                    return true;
                }

                if (target == player) {
                    player.sendMessage(ChatColor.RED + "You cannot donate hearts to yourself!");
                    return true;
                }

                StorageUtil.updatePlayer(player, "hearts", String.valueOf((max - amount)/2));

                double targetMax = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                if (targetMax <= 0) {
                    ElimUtil.revivePlayer(target, amount);
                }
                else {
                    target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(targetMax + amount);

                    StorageUtil.updatePlayer(target, "hearts", String.valueOf((targetMax + amount)/2));
                }

                player.sendMessage(ChatColor.GREEN + "You successfully gave " + amount/2 + " hearts to " + target.getName() + "!");
                target.sendMessage(ChatColor.GREEN + "You received " + amount/2 + " hearts from " + player.getName() + "!");
            }
        }
        return true;
    }

}
