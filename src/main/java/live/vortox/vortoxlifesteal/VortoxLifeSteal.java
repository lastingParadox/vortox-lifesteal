package live.vortox.vortoxlifesteal;

import live.vortox.vortoxlifesteal.commands.HealthCommand;
import live.vortox.vortoxlifesteal.commands.DonateCommand;
import live.vortox.vortoxlifesteal.commands.ReviveCommand;
import live.vortox.vortoxlifesteal.commands.WithdrawalCommand;
import live.vortox.vortoxlifesteal.items.ItemManager;
import live.vortox.vortoxlifesteal.listeners.HeartUseListener;
import live.vortox.vortoxlifesteal.listeners.PlayerDeathListener;
import live.vortox.vortoxlifesteal.listeners.PreventHeartEnderListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class VortoxLifeSteal extends JavaPlugin implements Listener {

    private final Logger log = getLogger();

    @Override
    public void onEnable() {
        // Plugin startup logic
        log.info("Started!");

        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
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
            this.getCommand("revive").setExecutor(new ReviveCommand());
        if (this.getConfig().getBoolean("withdrawal-command"))
            this.getCommand("withdraw").setExecutor(new WithdrawalCommand());

        ItemManager.init();

    }

    @Override
    public void onDisable() {
        log.info("Ended!");
    }
}
