package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PlayerDeathListener implements Listener {

    private final Random random;
    public PlayerDeathListener() {
        random = new Random();
    }

    @EventHandler()
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SPECTATOR);
        event.setDeathMessage("§c"+event.getDeathMessage());
        player.getWorld().strikeLightningEffect(player.getLocation());

        // drop 2-5 gold ingots on death
        event.getDrops().add(new ItemStack(Material.GOLD_INGOT, 2+random.nextInt(4)));

        Player killer = event.getPlayer().getKiller();
        if (killer != null) {
            killer.sendMessage(String.format("§aYou killed §f%s§a!", player.getName()));
            killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
        }
    }

}
