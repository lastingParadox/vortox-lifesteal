package live.vortox.vortoxlifesteal.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack heart;

    public static void init() {
        createHeart();
    }

    private static void createHeart() {
        ItemStack item = new ItemStack(Material.RED_DYE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dHeart");
        List<String> lore = new ArrayList<String>();
        lore.add("§7The essence of someone's life.");
        lore.add("§7Use it to add it to your own.");
        meta.setLore(lore);
        item.setItemMeta(meta);

        heart = item;

    }
}
