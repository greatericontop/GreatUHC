package io.github.greatericontop.greatuhc;

import io.github.greatericontop.greatuhc.customitems.CraftLimiter;
import io.github.greatericontop.greatuhc.customitems.CraftNotifications;
import io.github.greatericontop.greatuhc.customitems.Crafts;
import io.github.greatericontop.greatuhc.customitems.ItemEnhancementBookListener;
import io.github.greatericontop.greatuhc.customitems.ItemSugarCookieListener;
import io.github.greatericontop.greatuhc.customitems.ItemVitalityListener;
import io.github.greatericontop.greatuhc.customitems.PeriodicItemUpgradeListener;
import io.github.greatericontop.greatuhc.game.GameManager;
import io.github.greatericontop.greatuhc.game.StartCommand;
import io.github.greatericontop.greatuhc.game.pregame.KitSelectorGUIListener;
import io.github.greatericontop.greatuhc.game.pregame.PreGameManager;
import io.github.greatericontop.greatuhc.mechanics.AbsorptionEffectFix;
import io.github.greatericontop.greatuhc.mechanics.AntiAnvil;
import io.github.greatericontop.greatuhc.mechanics.CustomHealingListener;
import io.github.greatericontop.greatuhc.mechanics.DiamondSpreading;
import io.github.greatericontop.greatuhc.mechanics.ItemDropListener;
import io.github.greatericontop.greatuhc.mechanics.MobSpawning;
import io.github.greatericontop.greatuhc.mechanics.OldPVP;
import io.github.greatericontop.greatuhc.mechanics.PlayerDeathListener;
import io.github.greatericontop.greatuhc.mechanics.UHCCustomDamage;
import io.github.greatericontop.greatuhc.mechanics.WorldBorderDamage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GreatUHCMain extends JavaPlugin {

    public boolean uhcDoubleHeads = false;
    public boolean uhcPowerfulHeads = false;
    public boolean uhcSurvivalism = true;
    public boolean uhcMiningModifier = false;
    public boolean uhcStartingHeads = false;
    public boolean uhcRandomUltimate = false;
    public boolean debugMode = false;

    public CraftLimiter craftLimiter = null;
    public CraftNotifications craftNotifications = null;
    public GameManager gameManager = null;

    public void debugMsg(Player player, String str, Object... args) {
        if (debugMode) {
            player.sendMessage("§7[Debug] " + String.format(str, args));
        }
    }

    @Override
    public void onEnable() {

        PreGameManager preGameManager = new PreGameManager();
        this.getServer().getPluginManager().registerEvents(new KitSelectorGUIListener(preGameManager), this);
        gameManager = new GameManager(this, preGameManager);
        gameManager.registerRunnable();

        this.getServer().getPluginManager().registerEvents(new Crafts(), this);
        craftLimiter = new CraftLimiter(this);
        this.getServer().getPluginManager().registerEvents(craftLimiter, this);
        craftNotifications = new CraftNotifications();
        this.getServer().getPluginManager().registerEvents(craftNotifications, this);

        this.getServer().getPluginManager().registerEvents(new ItemEnhancementBookListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemSugarCookieListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemVitalityListener(), this);
        this.getServer().getPluginManager().registerEvents(new PeriodicItemUpgradeListener(gameManager), this);

        this.getServer().getPluginManager().registerEvents(new AbsorptionEffectFix(this), this);
        this.getServer().getPluginManager().registerEvents(new AntiAnvil(), this);
        this.getServer().getPluginManager().registerEvents(new CustomHealingListener(this), this);
        this.getServer().getPluginManager().registerEvents(new DiamondSpreading(), this);
        this.getServer().getPluginManager().registerEvents(new ItemDropListener(this), this);
        this.getServer().getPluginManager().registerEvents(new MobSpawning(), this);
        this.getServer().getPluginManager().registerEvents(new OldPVP(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new UHCCustomDamage(this), this);
        WorldBorderDamage.registerRunnable(this);

        GreatUHCCommand greatUHCCommand = new GreatUHCCommand(this);
        this.getCommand("greatuhc").setExecutor(greatUHCCommand);
        this.getCommand("greatuhc").setTabCompleter(greatUHCCommand);
        this.getCommand("uhcrecipes").setExecutor(new RecipesCommand());
        this.getCommand("uhcstart").setExecutor(new StartCommand(this));

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        } else {
            getLogger().warning("PlaceholderAPI not found, you will lose its functionality");
        }

        this.getLogger().info("GreatUHC finished setting up!");

        new BukkitRunnable() {
            public void run() {
                Crafts.registerCrafts(); // need to wait a few ticks because this needs to run AFTER other plugins initialize
                craftNotifications.initializeCraftLists();
                getLogger().info("Crafts ready!");
            }
        }.runTaskLater(this, 10L);

    }

}
