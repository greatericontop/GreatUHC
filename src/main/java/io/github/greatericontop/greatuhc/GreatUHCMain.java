package io.github.greatericontop.greatuhc;

import io.github.greatericontop.greatuhc.customitems.CraftLimiter;
import io.github.greatericontop.greatuhc.customitems.Crafts;
import io.github.greatericontop.greatuhc.customitems.EnhancementBookListener;
import io.github.greatericontop.greatuhc.mechanics.CustomHealingListener;
import io.github.greatericontop.greatuhc.mechanics.DiamondSpreading;
import io.github.greatericontop.greatuhc.mechanics.ItemDropListener;
import io.github.greatericontop.greatuhc.mechanics.MobSpawning;
import io.github.greatericontop.greatuhc.mechanics.OldPVP;
import io.github.greatericontop.greatuhc.mechanics.PlayerDeathListener;
import io.github.greatericontop.greatuhc.mechanics.UHCCustomDamage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GreatUHCMain extends JavaPlugin {

    public boolean uhcDoubleHeads = false;
    public boolean uhcPowerfulHeads = false;
    public boolean uhcSurvivalism = true;
    public boolean uhcMiningModifier = false;
    public boolean debugMode = false;

    public CraftLimiter craftLimiter = null;

    public void debugMsg(Player player, String str, Object... args) {
        if (debugMode) {
            player.sendMessage("§7[Debug] " + String.format(str, args));
        }
    }

    @Override
    public void onEnable() {

        this.getServer().getPluginManager().registerEvents(new Crafts(), this);
        craftLimiter = new CraftLimiter(this);
        this.getServer().getPluginManager().registerEvents(craftLimiter, this);
        this.getServer().getPluginManager().registerEvents(new EnhancementBookListener(), this);

        this.getServer().getPluginManager().registerEvents(new CustomHealingListener(this), this);
        this.getServer().getPluginManager().registerEvents(new DiamondSpreading(), this);
        this.getServer().getPluginManager().registerEvents(new ItemDropListener(this), this);
        this.getServer().getPluginManager().registerEvents(new MobSpawning(), this);
        this.getServer().getPluginManager().registerEvents(new OldPVP(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new UHCCustomDamage(this), this);

        this.getCommand("greatuhc").setExecutor(new GreatUHCCommand(this));

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        } else {
            getLogger().warning("PlaceholderAPI not found, you will lose its functionality");
        }

        this.getLogger().info("GreatUHC finished setting up!");

        new BukkitRunnable() {
            public void run() {
                Crafts.registerCrafts(); // need to wait a few ticks because this needs to run AFTER other plugins initialize
                getLogger().info("Crafts ready!");
            }
        }.runTaskLater(this, 10L);

    }

}
