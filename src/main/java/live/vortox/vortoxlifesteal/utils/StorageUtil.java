package live.vortox.vortoxlifesteal.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File file;
    private static List<PlayerStorage> playerList;

    private static void save() {

        if (playerList == null)
            return;

        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(playerList, writer);

            writer.close();
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to save to players.json!");
        }

    }

    public static void updatePlayer(OfflinePlayer player, String attribute, String change) {

        PlayerStorage user = findPlayer(player);

        if (user == null)
            return;

        if (attribute.equalsIgnoreCase("username"))
            user.setUsername(change);
        else if (attribute.equalsIgnoreCase("hearts"))
            user.setHearts(Double.parseDouble(change));
        else if (attribute.equalsIgnoreCase("eliminated"))
            user.setEliminated(Boolean.parseBoolean(change));
        else if (attribute.equalsIgnoreCase("revivalHearts"))
            user.setRevivalHearts(Double.parseDouble(change));
        else {
            Bukkit.getLogger().warning("Could not find command " + attribute + ".");
            return;
        }

        save();
    }

    public static void updatePlayer(PlayerStorage player, String attribute, String change) {

        for (PlayerStorage temp : playerList) {
            if (temp.getUuid().equals(player.getUuid())) {
                player = temp;
                break;
            }
        }

        if (attribute.equalsIgnoreCase("username"))
            player.setUsername(change);
        else if (attribute.equalsIgnoreCase("hearts"))
            player.setHearts(Double.parseDouble(change));
        else if (attribute.equalsIgnoreCase("eliminated"))
            player.setEliminated(Boolean.parseBoolean(change));
        else if (attribute.equalsIgnoreCase("revivalHearts"))
            player.setRevivalHearts(Double.parseDouble(change));
        else {
            Bukkit.getLogger().warning("Could not find command " + attribute + ".");
            return;
        }

        save();

    }

    public static List<PlayerStorage> returnPlayerList() throws IOException {
        Type listofPlayerStorage = new TypeToken<ArrayList<PlayerStorage>>() {}.getType();

        return gson.fromJson(new FileReader(file), listofPlayerStorage);
    }

    public static PlayerStorage findPlayer(OfflinePlayer player) {

        if (playerList == null) {
            Bukkit.getLogger().warning("Playerlist is null!");
            return null;
        }

        for (PlayerStorage index : playerList) {
            if (index.getUuid().equals(player.getUniqueId())) {
                return index;
            }
        }
        Bukkit.getLogger().warning("Could not find player " + player.getName() + ".");
        return null;
    }

    public static PlayerStorage findPlayer(String name) {

        if (playerList == null) {
            Bukkit.getLogger().warning("Playerlist is null!");
            return null;
        }

        for (PlayerStorage index : playerList) {
            if (index.getUsername().equalsIgnoreCase(name)) {
                return index;
            }
        }
        Bukkit.getLogger().warning("Could not find player " + name + ".");
        return null;
    }

    public static void addPlayer(Player player) {

        if (playerList == null)
            playerList = new ArrayList<>();

        if (findPlayer(player) != null)
            return;

        PlayerStorage user = new PlayerStorage(player);

        playerList.add(user);

        Bukkit.getLogger().info("Added " + player.getName() + " to the list!");

        save();
    }

    public static void createFile(String name) {
        File newFile = new File(VortoxLifeSteal.getPlugin().getDataFolder().getAbsolutePath(), name);

        try {
            if (newFile.createNewFile())
                Bukkit.getLogger().info("Created file!");

            file = newFile;
            playerList = returnPlayerList();
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to create file!");
        }
    }
}
