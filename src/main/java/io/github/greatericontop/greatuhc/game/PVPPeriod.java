package io.github.greatericontop.greatuhc.game;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;

public class PVPPeriod {

    public static void start(GameManager gameManager) {
        World overworld = gameManager.getOverworld();
        World nether = gameManager.getNether();

        Bukkit.broadcast(Component.text("§9------------------------------"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§cPVP is now enabled!"));
        Bukkit.broadcast(Component.text("§eThe border will now begin to shrink."));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§9------------------------------"));

        overworld.playSound(new Location(overworld, 0, 0, 0), Sound.ENTITY_ENDER_DRAGON_GROWL, 1000.0F, 1.0F);
        nether.playSound(new Location(nether, 0, 0, 0), Sound.ENTITY_ENDER_DRAGON_GROWL, 1000.0F, 1.0F);

        overworld.getWorldBorder().setSize(400.0, 1500L);
        nether.getWorldBorder().setSize(100.0, 1500L);

    }

}
