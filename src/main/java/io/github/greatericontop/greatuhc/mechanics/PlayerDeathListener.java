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
