package live.vortox.vortoxlifesteal.commands;

import live.vortox.vortoxlifesteal.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WithdrawalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            double health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health - 2);
            player.getInventory().addItem(ItemManager.heart);
            player.sendMessage(ChatColor.DARK_PURPLE + "You remove a heart from your health pool...");
        }
        return true;
    }

}
