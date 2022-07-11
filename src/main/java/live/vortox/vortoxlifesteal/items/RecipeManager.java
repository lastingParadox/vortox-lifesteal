package live.vortox.vortoxlifesteal.items;

import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {
    public static Recipe heartRecipe;
    public static Recipe heartFragmentRecipe;
    public static FileConfiguration config = VortoxLifeSteal.getPlugin().getConfig();

    public static void init() {
        createHeartRecipe();
        createHeartFragmentRecipe();

        if (heartRecipe != null)
            Bukkit.addRecipe(heartRecipe);
        if (heartFragmentRecipe != null)
            Bukkit.addRecipe(heartFragmentRecipe);
    }

    public static void createHeartRecipe() {
        String heartCraftingType = config.getString("heart-crafting");
        Recipe recipe = null;

        if (heartCraftingType.contains("fragment")) {
            int amount;
            try {
                amount = Integer.parseInt(heartCraftingType.substring(heartCraftingType.length() - 1));
                if (amount <= 0)
                    amount = 1;
                else if (amount >= 10)
                    amount = 10;
            } catch (NumberFormatException e) {
                amount = 4;
            }
            recipe = new ShapelessRecipe(new NamespacedKey(VortoxLifeSteal.getPlugin(), "heart"), ItemManager.heart);

            for (int i = 0; i < amount; i++)
                ((ShapelessRecipe) recipe).addIngredient(new RecipeChoice.ExactChoice(ItemManager.heartFragment));
        }
        else if (heartCraftingType.equalsIgnoreCase("smp")) {
            recipe = new ShapedRecipe(new NamespacedKey(VortoxLifeSteal.getPlugin(), "heart"), ItemManager.heart);

            ((ShapedRecipe) recipe).shape("XYX", "YZY", "XYX");
            ((ShapedRecipe) recipe).setIngredient('X', new RecipeChoice.ExactChoice(ItemManager.heartFragment));
            ((ShapedRecipe) recipe).setIngredient('Y', Material.DIAMOND_BLOCK);
            ((ShapedRecipe) recipe).setIngredient('Z', Material.ELYTRA);
        }
        else if (heartCraftingType.equalsIgnoreCase("custom")) {
            if (VortoxLifeSteal.getPlugin().getConfig().getString("custom.type").equalsIgnoreCase("shapeless")) {
            }
            else {
                recipe = new ShapedRecipe(new NamespacedKey(VortoxLifeSteal.getPlugin(), "heart"), ItemManager.heart);

                List<String> rows;
                rows = config.getStringList("custom.shaped");

                ((ShapedRecipe) recipe).shape("ABC", "DEF", "GHI");
                for (int i = 0; i < rows.size(); i++) {
                    String[] row = rows.get(i).split(" ");

                    for (int j = 0; j < 3; j++) {
                        row[j] = row[j].toUpperCase();

                        if (row[j].equalsIgnoreCase("HEART_FRAGMENT") && config.getBoolean("custom.uses-heart-fragments")) {
                            ((ShapedRecipe) recipe).setIngredient((char)(65 + 3*i + j), new RecipeChoice.ExactChoice(ItemManager.heartFragment));
                            continue;
                        }
                        else if (Material.getMaterial(row[j]) == null) {
                            Bukkit.getLogger().warning("Custom heart recipe value " + row[j] + " is invalid, stopping recipe creation.");
                            heartRecipe = null;
                            return;
                        }

                        ((ShapedRecipe) recipe).setIngredient((char)(65 + 3*i + j), Material.getMaterial(row[j]));
                    }
                }
            }
        }
        else {
            recipe = null;
        }

        heartRecipe = recipe;
    }

    public static void createHeartFragmentRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(VortoxLifeSteal.getPlugin(), "heartFragment"), ItemManager.heartFragment);

        recipe.shape("XXX", "XYX", "XXX");
        recipe.setIngredient('X', Material.GOLD_BLOCK);
        recipe.setIngredient('Y', Material.TOTEM_OF_UNDYING);

        heartFragmentRecipe = recipe;
    }
}
