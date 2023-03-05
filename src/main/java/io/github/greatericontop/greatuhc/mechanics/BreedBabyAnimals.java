package io.github.greatericontop.greatuhc.mechanics;

import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class BreedBabyAnimals implements Listener {

    @EventHandler()
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // Make breeding animals become adults. Naturally spawning animals can still be babies.
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.BREEDING)  return;
        if (event.getEntityType() != EntityType.CHICKEN
                && event.getEntityType() != EntityType.COW
                && event.getEntityType() != EntityType.PIG
                && event.getEntityType() != EntityType.SHEEP
        )  return;
        Animals animal = (Animals) event.getEntity();
        animal.setAge(-300); // 15 seconds to grow to adults
    }

}
