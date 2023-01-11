package io.github.greatericontop.greatuhc;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GreatUHCMain extends JavaPlugin {

    public boolean uhcDoubleHeads = false;
    public boolean uhcPowerfulHeads = false;
    public boolean uhcSurvivalism = true;
    public boolean debugMode = false;

    public void debugMsg(Player player, String str, String... args) {
        if (debugMode) {
            player.sendMessage("§7[Debug] " + String.format(str, (Object[]) args));
        }
    }

    @Override
    public void onEnable() {
        Crafts.registerCrafts();

        this.getServer().getPluginManager().registerEvents(new Crafts(), this);
        this.getServer().getPluginManager().registerEvents(new DamageEngine(this), this);
        this.getServer().getPluginManager().registerEvents(new DiamondSpreading(), this);
        this.getServer().getPluginManager().registerEvents(new HealingListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        this.getServer().getPluginManager().registerEvents(new OldPVP(), this);

        this.getCommand("greatuhc").setExecutor(new GreatUHCCommand(this));

        this.getLogger().info("GreatUHC finished setting up!");
    }

}
