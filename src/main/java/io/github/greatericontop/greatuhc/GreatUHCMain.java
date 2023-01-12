package io.github.greatericontop.greatuhc;

import io.github.greatericontop.greatuhc.customitems.CraftLimiter;
import io.github.greatericontop.greatuhc.customitems.Crafts;
import io.github.greatericontop.greatuhc.mechanics.DamageEngine;
import io.github.greatericontop.greatuhc.mechanics.DiamondSpreading;
import io.github.greatericontop.greatuhc.mechanics.HealingListener;
import io.github.greatericontop.greatuhc.mechanics.ItemDropListener;
import io.github.greatericontop.greatuhc.mechanics.OldPVP;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GreatUHCMain extends JavaPlugin {

    public boolean uhcDoubleHeads = false;
    public boolean uhcPowerfulHeads = false;
    public boolean uhcSurvivalism = true;
    public boolean debugMode = false;

    public CraftLimiter craftLimiter = null;

    public void debugMsg(Player player, String str, Object... args) {
        if (debugMode) {
            player.sendMessage("§7[Debug] " + String.format(str, args));
        }
    }

    @Override
    public void onEnable() {

        Crafts.registerCrafts();
        this.getServer().getPluginManager().registerEvents(new Crafts(), this);
        craftLimiter = new CraftLimiter();
        this.getServer().getPluginManager().registerEvents(craftLimiter, this);

        this.getServer().getPluginManager().registerEvents(new DamageEngine(this), this);
        this.getServer().getPluginManager().registerEvents(new DiamondSpreading(), this);
        this.getServer().getPluginManager().registerEvents(new HealingListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        this.getServer().getPluginManager().registerEvents(new OldPVP(), this);

        this.getCommand("greatuhc").setExecutor(new GreatUHCCommand(this));

        this.getLogger().info("GreatUHC finished setting up!");
    }

}
