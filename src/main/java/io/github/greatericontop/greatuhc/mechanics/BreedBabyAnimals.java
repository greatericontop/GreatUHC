package io.github.greatericontop.greatuhc.mechanics;

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
