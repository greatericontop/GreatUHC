package io.github.greatericontop.greatuhc;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class FightingDisplay implements Listener {

    private int clock;
    private final Map<Player, Player> targets = new HashMap<>();
    private final Map<Player, Integer> expiry = new HashMap<>();
    private final GreatUHCMain plugin;
    public FightingDisplay(GreatUHCMain plugin) {
        this.plugin = plugin;
        clock = 0;
        new BukkitRunnable() {
            public void run() {
                clock++;
            }
        }.runTaskTimer(plugin, 5L, 5L); // don't need to be super precise
    }

    /*
     * Return the information that a placeholder would use for this.
     */
    public String getInformation(Player player) {
        Player target = targets.get(player);
        if (target == null)  return null;
        if (expiry.get(player) < clock || target.isDead()) {
            targets.remove(player);
            expiry.remove(player);
            return null;
        }
        return String.format("§e%s §f- §c%.0f §fHP", target.getDisplayName(), target.getHealth()+target.getAbsorptionAmount());
    }

    private void doFightingDisplay(Player attacker, Player victim) {
        targets.put(attacker, victim);
        expiry.put(attacker, clock + 32); // 8 seconds
    }

    @EventHandler()
    public void onDamageByEntityPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker))  return;
        if (!(event.getEntity() instanceof Player victim))  return;
        doFightingDisplay(attacker, victim);
    }

    @EventHandler()
    public void onDamageByEntityProjectile(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Projectile projectile))  return;
        if (!(projectile.getShooter() instanceof Player shooter))  return;
        if (!(event.getEntity() instanceof Player victim))  return;
        doFightingDisplay(shooter, victim);
    }

}
