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

        if (spawnedEntity.getWorld().getEnvironment() == World.Environment.NETHER) {
            if (Math.random() < 0.05) {
                spawnedEntity.getWorld().spawnEntity(spawnedEntity.getLocation(), EntityType.BLAZE, true);
            }
        }

        if (spawnedEntity.getWorld().getEnvironment() == World.Environment.NORMAL) {
            if (Math.random() < 0.01) {
                spawnedEntity.getWorld().spawnEntity(spawnedEntity.getLocation(), EntityType.ENDERMAN, true);
            }
        }
    }

}
