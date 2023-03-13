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

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemSugarCookieListener implements Listener {

    @EventHandler()
    public void onEat(PlayerItemConsumeEvent event) {
        ItemMeta im = event.getItem().getItemMeta();
        if (im == null)  return;
        if (im.getLore() == null)  return;
        if (im.getLore().get(0).equals("Sugary")) {
            Player player = event.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 400, 3));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        }
    }

}
