package live.vortox.vortoxlifesteal.items;

import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack heart;
    public static ItemStack heartFragment;
    public static FileConfiguration config = VortoxLifeSteal.getPlugin().getConfig();

    public static void init() {
        createHeart();
        createHeartFragment();
    }

    private static void createHeart() {
        ItemStack item = new ItemStack(Material.RED_DYE, 1);
        item.addUnsafeEnchantment(Enchantment.LUCK, 1);

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName("§dHeart");
        List<String> lore = new ArrayList<>();
        lore.add("§7The essence of someone's life.");
        lore.add("§7Use it to gain one heart.");
        meta.setLore(lore);
        item.setItemMeta(meta);

        heart = item;

    }

    private static void createHeartFragment() {
        String heartCrafting = config.getString("heart-crafting");

        ItemStack item = new ItemStack(Material.NETHER_WART, 1);
        item.addUnsafeEnchantment(Enchantment.LUCK, 1);

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName("§dHeart Fragment");
        List<String> lore = new ArrayList<>();
        lore.add("§7A portion of an essence of someone's life.");
        if (heartCrafting.contains("fragment") || heartCrafting.equalsIgnoreCase("smp") || config.getBoolean("custom.uses-heart-fragments"))
            lore.add("§7Use them to craft for a full heart.");
        meta.setLore(lore);
        item.setItemMeta(meta);

        heartFragment = item;
    }
}
