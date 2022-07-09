package live.vortox.vortoxlifesteal.commands;

import live.vortox.vortoxlifesteal.items.ItemManager;
import live.vortox.vortoxlifesteal.utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class WithdrawalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final String example = ChatColor.RED + "Example: /withdraw <integer>";
            Player player = (Player) sender;
            double health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

            if (health == 2) {
                player.sendMessage(ChatColor.RED + "You don't have enough health to withdraw!");
                return true;
            }

            if (args.length == 0) {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health - 2);

                try {
                    StorageUtil.updatePlayer(player, "hearts", String.valueOf((health - 2)/2));
                } catch (IOException e) {
                    Bukkit.getLogger().warning("Unable to update " + player.getName() + " hearts in storage.");
                }

                player.getInventory().addItem(ItemManager.heart);
                player.sendMessage(ChatColor.DARK_PURPLE + "You convert a " + ChatColor.LIGHT_PURPLE + "heart "
                        + ChatColor.DARK_PURPLE + "to your inventory...");
            }
            else {
                int amount;
                try {
                    amount = Math.abs(Integer.parseInt(args[0]));
                    amount *= 2;
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Amount provided is not an integer number of hearts.");
                    player.sendMessage(example);
                    return true;
                }

                if (health - amount <= 0) {
                    player.sendMessage(ChatColor.RED + "You don't have enough health to withdraw!");
                    return true;
                }

                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health - amount);

                try {
                    StorageUtil.updatePlayer(player, "hearts", String.valueOf((health - amount)/2));
                } catch (IOException e) {
                    Bukkit.getLogger().warning("Unable to update " + player.getName() + " hearts in storage.");
                }

                for (int i = 0; i < (amount/2); i++) {
                    player.getInventory().addItem(ItemManager.heart);
                }

                if (amount == 2)
                    player.sendMessage(ChatColor.DARK_PURPLE + "You convert a " + ChatColor.LIGHT_PURPLE + "heart " +
                            ChatColor.DARK_PURPLE + "to your inventory...");
                else
                    player.sendMessage(ChatColor.DARK_PURPLE + "You convert " + amount/2 + ChatColor.LIGHT_PURPLE
                            + " hearts " + ChatColor.DARK_PURPLE + "to your inventory...");

            }

        }
        return true;
    }

}
