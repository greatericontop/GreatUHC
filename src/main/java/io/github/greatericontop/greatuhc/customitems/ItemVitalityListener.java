package io.github.greatericontop.greatuhc.customitems;

/*
 * Copyright (C) 2023-present greateric.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty  of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
        thrownPotion.getWorld().spawnParticle(Particle.SQUID_INK, thrownPotion.getLocation(), 40, 0.0, 0.0, 0.0, 0.08);
        for (Entity entity0 : thrownPotion.getNearbyEntities(4.0, 4.0, 4.0)) {
            if (!(entity0 instanceof LivingEntity entity))  continue;
            // Y coordinate is more lenient than Minecraft's default potion effect application.
            // You must be 4.0 blocks Euclidean distance. There is no Y limit.
            // All entities receive the FULL duration, rather than reduced duration.
            double distanceSquared = entity.getEyeLocation().distanceSquared(thrownPotion.getLocation());
            if (distanceSquared <= MAX_EUCLIDEAN_DISTANCE_2) {
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
