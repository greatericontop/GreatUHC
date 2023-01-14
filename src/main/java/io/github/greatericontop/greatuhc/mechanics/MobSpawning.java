package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawning implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) {
            return;
        }
        LivingEntity spawnedEntity = event.getEntity();
        if (spawnedEntity.getWorld().getEnvironment() != World.Environment.NETHER) {
            return; // require a mob spawned in the nether
        }

        if (Math.random() < 0.04) {
            spawnedEntity.getWorld().spawnEntity(spawnedEntity.getLocation(), EntityType.BLAZE, true);
        }
    }

}
