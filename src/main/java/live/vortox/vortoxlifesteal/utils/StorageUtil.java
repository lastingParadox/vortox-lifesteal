package live.vortox.vortoxlifesteal.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import live.vortox.vortoxlifesteal.VortoxLifeSteal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File file;

    private static void save(List<PlayerStorage> players) throws IOException {
        if (players == null)
            return;

        Writer writer = new FileWriter(file, false);
        gson.toJson(players, writer);

        writer.close();
    }

    public static void updatePlayer(Player player, String attribute, String change) throws IOException {

        List<PlayerStorage> playerList = returnPlayerList();

        PlayerStorage user = findPlayer(playerList, player);

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

        save(playerList);

    }

    public static List<PlayerStorage> returnPlayerList() throws IOException {
        Reader reader = new FileReader(file);

        Type listofPlayerStorage = new TypeToken<ArrayList<PlayerStorage>>() {}.getType();
        return gson.fromJson(reader, listofPlayerStorage);
    }

    public static PlayerStorage findPlayer(List<PlayerStorage> playerList, Player player) {

        if (playerList == null)
            return null;

        for (PlayerStorage index : playerList) {
            if (index.getUuid().equals(player.getUniqueId())) {
                return index;
            }
        }
        Bukkit.getLogger().warning("Could not find player " + player.getName() + ".");
        return null;
    }

    public static PlayerStorage findPlayer(List<PlayerStorage> playerList, String name) {

        if (playerList == null)
            return null;

        for (PlayerStorage index : playerList) {
            if (index.getUsername().equalsIgnoreCase(name)) {
                return index;
            }
        }
        Bukkit.getLogger().warning("Could not find player " + name + ".");
        return null;
    }

    public static void addPlayer(Player player) throws IOException {
        List<PlayerStorage> playerList = returnPlayerList();

        if (playerList == null)
            playerList = new ArrayList<>();

        if (findPlayer(playerList, player) != null)
            return;

        PlayerStorage user = new PlayerStorage(player);

        playerList.add(user);

        save(playerList);
    }

    public static void createFile(String name) {
        File newFile = new File(VortoxLifeSteal.getPlugin().getDataFolder().getAbsolutePath() + "/" + name);
        file = newFile;
        try {
            if (!newFile.createNewFile())
                Bukkit.getLogger().info("Created file!");
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to create file!");
        }
    }
}
