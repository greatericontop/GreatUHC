package io.github.greatericontop.greatuhc.mechanics;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.ProjectileSource;

public class DamageEngine implements Listener {

    private final GreatUHCMain plugin;
    public DamageEngine(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST) // this will run first before all the other events
    public void onDamageSurvivalism(EntityDamageEvent event) {
        if (!plugin.uhcSurvivalism)  return;
        if (!(event.getEntity() instanceof Player))  return;
        if (event.isCancelled())  return;
        Player player = (Player) event.getEntity();
        DamageCause cause = event.getCause();

        if (cause == DamageCause.VOID
                || cause == DamageCause.ENTITY_ATTACK
                || cause == DamageCause.ENTITY_SWEEP_ATTACK
                || cause == DamageCause.PROJECTILE) {
            // TODO: check this in EntityDamageByEntityEvent
            return;
        }
        if (cause == DamageCause.MAGIC || cause == DamageCause.POISON || cause == DamageCause.WITHER) {
            plugin.debugMsg(player, "survivalism: potion damage cause, slightly reducing damage");
            event.setDamage(event.getDamage() * 0.8);
            return;
        }
        plugin.debugMsg(player, "survivalism: other damage cause, reducing damage");
        event.setDamage(event.getDamage() * 0.6);
    }

    @EventHandler(priority = EventPriority.LOWEST) // same here
    public void onDamageByEntitySurvivalism(EntityDamageByEntityEvent event) {
        if (!plugin.uhcSurvivalism)  return;
        if (!(event.getEntity() instanceof Player))  return;
        if (event.isCancelled())  return;
        Entity damagingEntity = event.getDamager();
        Player player = (Player) event.getEntity();
        DamageCause cause = event.getCause();

        if (cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.ENTITY_SWEEP_ATTACK) {
            if (!(damagingEntity instanceof Player)) {
                plugin.debugMsg(player, "survivalism: entity attack, reducing damage");
                event.setDamage(event.getDamage() * 0.6);
            }
            return;
        }

        if (cause == DamageCause.PROJECTILE) {
            ProjectileSource shooter = ((Projectile) damagingEntity).getShooter();
            if (!(shooter instanceof Player)) {
                plugin.debugMsg(player, "survivalism: projectile hit, reducing damage");
                event.setDamage(event.getDamage() * 0.6);
            }
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOW) // normal damage handler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))  return;
        if (event.isCancelled())  return;
        event.setDamage(event.getDamage() * 0.89); // 11% damage reduction for everything
    }

}
