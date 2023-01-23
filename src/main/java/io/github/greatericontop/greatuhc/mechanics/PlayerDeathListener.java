package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeathListener {

    @EventHandler()
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SPECTATOR);
        event.setDeathMessage("§c"+event.getDeathMessage());
        player.getWorld().strikeLightningEffect(player.getLocation());

        Player killer = event.getPlayer().getKiller();
        if (killer != null) {
            killer.sendMessage(String.format("§aYou killed §f%s§a!", player.getName()));
            killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
        }
    }

}
