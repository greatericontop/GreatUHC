package io.github.greatericontop.greatuhc;

import org.bukkit.plugin.java.JavaPlugin;

public class GreatUHCMain extends JavaPlugin {

    @Override
    public void onEnable() {
        Crafts.registerCrafts();
        this.getServer().getPluginManager().registerEvents(new Crafts(), this);
        this.getServer().getPluginManager().registerEvents(new DiamondSpreading(), this);
        this.getServer().getPluginManager().registerEvents(new HealingListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        this.getLogger().info("GreatUHC finished setting up!");
    }

}
