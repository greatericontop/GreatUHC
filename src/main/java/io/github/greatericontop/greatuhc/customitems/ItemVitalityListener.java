package io.github.greatericontop.greatuhc.customitems;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemVitalityListener implements Listener {
    private static final double MAX_EUCLIDEAN_DISTANCE_2 = 4.0 * 4.0;
    private static final double MAX_Y_DELTA = 2.125;

    // (UUID of potion) ---> (UUID of its owner)
    private Map<UUID, UUID> owners = new HashMap<>();

    @EventHandler()
    public void onPotionThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof ThrownPotion thrownPotion))  return;
        if (!(thrownPotion.getShooter() instanceof Player player))  return;
        PotionMeta potIM = (PotionMeta) thrownPotion.getItem().getItemMeta();
        if (potIM == null)  return;
        if (potIM.getLore() == null)  return;
        if (!potIM.getLore().get(0).equals("Vital"))  return;
        owners.put(thrownPotion.getUniqueId(), player.getUniqueId());
    }

    @EventHandler()
    public void onThrownPotionLand(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof ThrownPotion thrownPotion))  return;
        UUID potionOwnerUUID = owners.get(thrownPotion.getUniqueId());
        if (potionOwnerUUID == null)  return; // only catch vitality pots since they have an owner
        thrownPotion.getWorld().spawnParticle(Particle.SQUID_INK, thrownPotion.getLocation(), 30, 0.0, 0.0, 0.0, 0.03);
        for (Entity entity0 : thrownPotion.getNearbyEntities(4.0, 4.0, 4.0)) {
            if (!(entity0 instanceof LivingEntity entity))  continue;
            // We'll use a simplified version of what Minecraft uses
            // You must be 4.0 blocks Euclidean distance, and your Y coordinate must be within +/- 2.125 blocks.
            // All entities receive the FULL duration, rather than reduced duration.
            double distanceSquared = entity.getEyeLocation().distanceSquared(thrownPotion.getLocation());
            double yDelta = Math.abs(entity.getEyeLocation().getY() - thrownPotion.getLocation().getY());
            if (distanceSquared <= MAX_EUCLIDEAN_DISTANCE_2 && yDelta <= MAX_Y_DELTA) {
                if (entity.getUniqueId().equals(potionOwnerUUID)) {
                    // Self
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 0));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 240, 0));
                } else {
                    // Other
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 0));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 240, 0));
                }
            }
        }
        owners.remove(thrownPotion.getUniqueId());
    }

}