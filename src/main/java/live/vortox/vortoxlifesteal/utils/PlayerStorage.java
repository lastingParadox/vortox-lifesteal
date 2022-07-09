package live.vortox.vortoxlifesteal.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerStorage {

    private String username;
    private UUID uuid;
    private double hearts;
    private boolean eliminated;
    private double revivalHearts;

    public PlayerStorage(Player player) {
        this.username = player.getName();
        this.uuid = player.getUniqueId();
        this.hearts = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 2;
        this.eliminated = false;
        this.revivalHearts = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getHearts() {
        return hearts;
    }

    public void setHearts(double hearts) {
        this.hearts = hearts;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public double getRevivalHearts() {
        return revivalHearts;
    }

    public void setRevivalHearts(double revivalHearts) {
        this.revivalHearts = revivalHearts;
    }


}
