package live.vortox.vortoxlifesteal;

import live.vortox.vortoxlifesteal.commands.*;
import live.vortox.vortoxlifesteal.items.ItemManager;
import live.vortox.vortoxlifesteal.items.RecipeManager;
import live.vortox.vortoxlifesteal.listeners.*;

import live.vortox.vortoxlifesteal.utils.StorageUtil;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class VortoxLifeSteal extends JavaPlugin implements Listener {

    private final Logger log = getLogger();
    private static VortoxLifeSteal plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        log.info("Started!");

        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new BannedPlayerListener(), this);

        if (this.getConfig().getBoolean("automatic-health"))
            log.info("Automatic health enabled!");
        else
            log.info("Automatic health disabled. Players will drop hearts in PVP.");

        getServer().getPluginManager().registerEvents(new HeartUseListener(), this);

        if (!this.getConfig().getBoolean("allow-ender-hearts")) {
            getServer().getPluginManager().registerEvents(new PreventHeartEnderListener(), this);
            log.info("Hearts in ender chests disabled!");
        } else
            log.info("Hearts in ender chests enabled!");

        if (this.getConfig().getBoolean("donate-command"))
            this.getCommand("donate").setExecutor(new DonateCommand());
        if (this.getConfig().getBoolean("health-command"))
            this.getCommand("health").setExecutor(new HealthCommand());
        if (this.getConfig().getBoolean("revive-command"))
            this.getCommand("revive").setExecutor(new ReviveCommand(this));
        if (this.getConfig().getBoolean("withdrawal-command"))
            this.getCommand("withdraw").setExecutor(new WithdrawalCommand());

        ItemManager.init();
        RecipeManager.init();

        StorageUtil.createFile("players.json");
    }

    @Override
    public void onDisable() {
        log.info("Ended!");
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}